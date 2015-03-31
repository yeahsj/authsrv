package net.suntec.oauthsrv.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.framework.constant.AppConstant;
import net.suntec.framework.util.ASLogger;
import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.framework.OauthAppConfig;
import net.suntec.oauthsrv.framework.ResourceConfig;
import net.suntec.oauthsrv.framework.dto.ServerConfig;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:50:11
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Controller
@RequestMapping(value = "/config")
public class ManagerAction {
	// Logger logger = Logger.getLogger(AppInitServlet.class.getName());
	private final ASLogger logger = new ASLogger(MainAction.class);

	@RequestMapping(value = "/index")
	public String index(@ModelAttribute AppBase appBase, Model model) {
		return "/config/index";
	}

	@RequestMapping(value = "/reloadConfig")
	public String reloadConfig(HttpServletRequest req, HttpServletResponse res) {
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		ServerConfig serverConfig = ResourceConfig.getInstance()
				.getServerConfig();
		if (serverConfig.getManagerUserName().equals(userName)
				&& serverConfig.getManagerPassword().equals(password)) {
			String configFile = req.getSession().getServletContext()
					.getInitParameter("systemConfigPath");
			try {
				ResourceConfig.getInstance().init(configFile);
			} catch (SAXException e) {
				logger.error(e.getMessage());
				req.setAttribute("errMsg", e.getMessage());
				return AppConstant.ERROR_URL;
			} catch (IOException e) {
				logger.error(e.getMessage());
				req.setAttribute("errMsg", e.getMessage());
				return AppConstant.ERROR_URL;
			}
			OauthAppConfig oauthAppConfig = OauthAppConfig.getInstance();
			oauthAppConfig.reload(req.getSession().getServletContext());
			return "/config/index";
		} else {
			req.setAttribute("errMsg", " userName or password Incorrect ");
			return AppConstant.ERROR_URL;
		}

	}
}
