package com.order.erp.web.plugin.wx.controller;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/plugin/wx")
public class WxController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/callback")
	@ResponseBody
	public String index(@RequestParam("signature") String signature, @RequestParam("echostr") String echostr, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, ModelMap model) {
		/*
		String url = "http://info.jefei.com/plugin/wx/callback";
		String appId = "wx849c5f560cb94928";
		String token = "wxf_jf_token";
		String encodingAesKey = "Q30vHiRYAQMx1huE7aH6AbYkrN5ymRE6dtSMZ5TAFZv";
		String timestamp = "1409304348";
		String nonce = "xxxxxx";
		String replyMsg = " 中文<xml><ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
		try{
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
			System.out.println("加密后: " + mingwen);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(mingwen);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
			String encrypt = nodelist1.item(0).getTextContent();
			String msgSignature = nodelist2.item(0).getTextContent();
			String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
			String fromXML = String.format(format, encrypt);
			// 第三方收到公众号平台发送的消息
			String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
			System.out.println("解密后明文: " + result2);
			//pc.verifyUrl(null, null, null, null);
		}catch(AesException e){
			
		}catch(ParserConfigurationException e){
			
		}catch(SAXException e){
			
		}catch(IOException e){
			
		}
		*/
		
		String token = "wxf_jf_token";
		String[] str = { token, timestamp, nonce }; 
		Arrays.sort(str); // 字典序排序  
		String bigStr = str[0] + str[1] + str[2];
		String digest = DigestUtils.sha1Hex(bigStr);

		//logger.info(token);
		//logger.info(timestamp);
		//logger.info(nonce);
		//logger.info(bigStr);
		//logger.info(digest);
		//logger.info(signature);
		if (digest.equals(signature)) {
			return echostr;
		}else{
			return "";
		}
	}

}
