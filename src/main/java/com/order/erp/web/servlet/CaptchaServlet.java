package com.order.erp.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

@WebServlet(name="CaptchaServlet" ,urlPatterns={"/captcha.jpg"},loadOnStartup=1,
initParams={
	//@WebInitParam(name="name",value="xiazdong"),
	//@WebInitParam(name="age",value="20")
})
public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Properties props = new Properties();
	private Producer kaptchaProducer = null;
	private String sessionKeyValue = null;
	private String sessionKeyDateValue = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CaptchaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化过滤器.将配置文件的参数文件赋值
	 * 
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);

		// Switch off disk based caching.
		ImageIO.setUseCache(false);

		Enumeration<?> initParams = conf.getInitParameterNames();
		while (initParams.hasMoreElements()) {
			String key = (String) initParams.nextElement();
			String value = conf.getInitParameter(key);
			this.props.put(key, value);
		}

		this.props.put("kaptcha.textproducer.char.length", "4");
		this.props.put("kaptcha.border.color", "lightGray");
		this.props.put("kaptcha.noise.color", "lightGray");
		this.props.put("kaptcha.textproducer.font.color", "blue");
		
		
		Config config = new Config(this.props);
		this.kaptchaProducer = config.getProducerImpl();
		this.sessionKeyValue = config.getSessionKey();
		this.sessionKeyDateValue = config.getSessionDate();
		
	}

	/**
	 * 获取图片只会有get方法 kaptcha.border 是否有边框 默认为true 我们可以自己设置yes，no
	 * kaptcha.border.color 边框颜色 默认为Color.BLACK kaptcha.border.thickness 边框粗细度
	 * 默认为1 kaptcha.producer.impl 验证码生成器 默认为DefaultKaptcha
	 * kaptcha.textproducer.impl 验证码文本生成器 默认为DefaultTextCreator
	 * kaptcha.textproducer.char.string 验证码文本字符内容范围 默认为abcde2345678gfynmnpwx
	 * kaptcha.textproducer.char.length 验证码文本字符长度 默认为5
	 * kaptcha.textproducer.font.names 验证码文本字体样式 默认为new Font("Arial", 1,
	 * fontSize), new Font("Courier", 1, fontSize)
	 * kaptcha.textproducer.font.size 验证码文本字符大小 默认为40
	 * kaptcha.textproducer.font.color 验证码文本字符颜色 默认为Color.BLACK
	 * kaptcha.textproducer.char.space 验证码文本字符间距 默认为2 kaptcha.noise.impl
	 * 验证码噪点生成对象 默认为DefaultNoise kaptcha.noise.color 验证码噪点颜色 默认为Color.BLACK
	 * kaptcha.obscurificator.impl 验证码样式引擎 默认为WaterRipple kaptcha.word.impl
	 * 验证码文本字符渲染 默认为DefaultWordRenderer kaptcha.background.impl 验证码背景生成器
	 * 默认为DefaultBackground kaptcha.background.clear.from 验证码背景颜色渐进
	 * 默认为Color.LIGHT_GRAY kaptcha.background.clear.to 验证码背景颜色渐进 默认为Color.WHITE
	 * kaptcha.image.width 验证码图片宽度 默认为200 kaptcha.image.height 验证码图片高度 默认为50
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set to expire far in the past.
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		// return a jpeg
		response.setContentType("image/jpeg");

		// create the text for the image
		String capText = this.kaptchaProducer.createText();

		// store the text in the session
		request.getSession().setAttribute(this.sessionKeyValue, capText);

		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		request.getSession().setAttribute(this.sessionKeyDateValue, new Date());

		// create the image with the text
		BufferedImage bi = this.kaptchaProducer.createImage(capText);

		ServletOutputStream out = response.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
		// response.getWriter().append("Served
		// at:").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
