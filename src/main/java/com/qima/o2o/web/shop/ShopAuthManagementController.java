`package com.qima.o2o.web.shop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.qima.o2o.dto.ShopAuthMapExecution;
import com.qima.o2o.entity.PersonInfo;
import com.qima.o2o.entity.Shop;
import com.qima.o2o.entity.ShopAuthMap;
import com.qima.o2o.enums.ShopAuthMapStateEnum;
import com.qima.o2o.service.ShopAuthMapService;
import com.qima.o2o.util.CodeUtil;
import com.qima.o2o.util.HttpServletRequestUtil;
import com.qima.o2o.util.ShortNetAddressUtil;

@Controller
@RequestMapping("/shop")
public class ShopAuthManagementController {
	@Autowired
	private ShopAuthMapService shopAuthMapService;

	@RequestMapping(value = "/listshopauthmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopAuthMapsByShop(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
				&& (currentShop.getShopId() != null)) {
			ShopAuthMapExecution se = shopAuthMapService
					.listShopAuthMapByShopId(currentShop.getShopId(),
							pageIndex, pageSize);
			modelMap.put("shopAuthMapList", se.getShopAuthMapList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopauthmapbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopAuthMapById(@RequestParam Long shopAuthId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (shopAuthId != null && shopAuthId > -1) {
			ShopAuthMap shopAuthMap = shopAuthMapService
					.getShopAuthMapById(shopAuthId);
			modelMap.put("shopAuthMap", shopAuthMap);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopAuthId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/addshopauthmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addShopAuthMap(String shopAuthMapStr,HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;
		try {
			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (shopAuthMap != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				if (currentShop.getOwnerId() != user.getUserId()) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "无操作权限");
					return modelMap;
				}
				shopAuthMap.setShopId(currentShop.getShopId());
				shopAuthMap.setEmployeeId(user.getUserId());
				ShopAuthMapExecution se = shopAuthMapService.addShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入授权信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyshopauthmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShopAuthMap(String shopAuthMapStr,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;
		try {
			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
				shopAuthMap.setShopId(currentShop.getShopId());
				shopAuthMap.setEmployeeId(user.getUserId());
				
				if(!checkPermission(shopAuthMap.getShopAuthId())) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "无法对店家本身权限做操作(已是店铺的最高权限)");
					return modelMap;
				}
				
				ShopAuthMapExecution se = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入要修改的授权信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeshopauthmap", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> removeShopAuthMap(Long shopAuthId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (shopAuthId != null && shopAuthId > 0) {
			try {
				ShopAuthMapExecution se = shopAuthMapService
						.removeShopAuthMap(shopAuthId);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个授权进行删除");
		}
		return modelMap;
	}
	
	private boolean checkPermission(Long shopAuthId) {
		ShopAuthMap grantedPerson = shopAuthMapService.getShopAuthMapById(shopAuthId);
		if(grantedPerson.getTitleFlag() == 0) {
			//若是店家本身,不能操作
			return false;
		} else {
			return true;
		}
	}
	
	private static String urlPrefix;
	private static String urlMiddle;
	private static String urlSuffix;
	private static String authUrl;
	
	@Value("${wechat.prefix}")
	public static void setUrlPrefix(String urlPrefix) {
		ShopAuthManagementController.urlPrefix = urlPrefix;
	}

	@Value("wechat.middle")
	public static void setUrlMiddle(String urlMiddle) {
		ShopAuthManagementController.urlMiddle = urlMiddle;
	}

	@Value("wechat.suffix")
	public static void setUrlSuffix(String urlSuffix) {
		ShopAuthManagementController.urlSuffix = urlSuffix;
	}

	@Value("wechat.auth.url")
	public static void setAuthUrl(String authUrl) {
		ShopAuthManagementController.authUrl = authUrl;
	}

	@RequestMapping(value = "/generateqrcode4shopauth", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRCode4ShopAuth(HttpServletRequest request,
			HttpServletResponse response) {
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		if(shop != null && shop.getShopId() != null) {
			long timpStamp = System.currentTimeMillis();
			String content = "{aaashopIdaaa:"+shop.getShopId()+",aaacreateTimeaaa:"+timpStamp+"}";
			try {
				String longUrl = urlPrefix + authUrl + urlMiddle + URLEncoder.encode("content", "UTF-8") + urlSuffix;
				String shortUrl = ShortNetAddressUtil.getShortURL(longUrl);
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
				MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
