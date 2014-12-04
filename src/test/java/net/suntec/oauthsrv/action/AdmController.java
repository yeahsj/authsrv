package net.suntec.oauthsrv.action;

import java.util.List;

import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.dto.AppInfo;
import net.suntec.oauthsrv.service.AdmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述: 
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:26:16
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
@Controller
@RequestMapping(value = "/adm")
public class AdmController {
	@Autowired
	AdmService admService = null;

	@RequestMapping(value = "/create")
	public String createForm() {
		return "adm/create";
	}

	@RequestMapping(value = "/add")
	public String add(@ModelAttribute AppInfo appInfo, Model model) {
		model.addAttribute("appId", appInfo.getAppId());
		return "adm/detail";
	}

	@RequestMapping(value = "/{admId}/detail")
	public String detail(@PathVariable("admId") String admId, Model model) {
		model.addAttribute("admId", admId);
		return "adm/detail";
	}

	@RequestMapping(value = "/query")
	public String query(@ModelAttribute AppInfo appInfo, Model model) {
		List<AppInfo> datas = admService.list(appInfo);
		model.addAttribute("rows", datas);

		return "adm/query";
	}

	@RequestMapping(value = "/base/query")
	public String query(@ModelAttribute AppBase appInfo, Model model) {
		List<AppBase> datas = admService.listBase(appInfo);
		model.addAttribute("rows", datas);

		return "adm/base/query";
	}
}
