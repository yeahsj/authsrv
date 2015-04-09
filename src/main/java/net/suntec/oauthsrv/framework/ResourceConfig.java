package net.suntec.oauthsrv.framework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.suntec.framework.iauto.client.dto.IautoConfigDTO;
import net.suntec.oauthsrv.framework.dto.ServerConfig;
import net.suntec.oauthsrv.framework.dto.SystemConfig;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 加载SystemConfig.xml
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:27
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class ResourceConfig {
	private static ResourceConfig instace = null;

	private boolean isInit = false;

	private SystemConfig systemConfig = null;
	private String localServer = null;
	private final Logger logger = LoggerFactory.getLogger(ResourceConfig.class);

	private ResourceConfig() {
	}

	public static ResourceConfig getInstance() {
		return instace;
	}

	public void destroyed() {
		systemConfig = null;
	}

	public void init(String configFile) throws SAXException, IOException {
		// String absolutePath = SystemUtil.getConfigPath(configFile);
		// logger.info("config path is " + absolutePath );
		logger.debug("ResourceConfig init .............. ");
		Digester digester = new Digester();

		digester.setValidating(false);
		digester.setClassLoader(SystemConfig.class.getClassLoader());

		digester.addObjectCreate("SystemConfig", SystemConfig.class.getName(),
				"className");
		digester.addSetProperties("SystemConfig");
		digester.addSetNext("SystemConfig", "setSystemConfig",
				SystemConfig.class.getName());

		digester.addObjectCreate("SystemConfig/ServerConfig",
				ServerConfig.class.getName(), "className");
		digester.addSetProperties("SystemConfig/ServerConfig");
		digester.addSetNext("SystemConfig/ServerConfig", "setServerConfig",
				ServerConfig.class.getName());

		digester.addObjectCreate("SystemConfig/IautoConfig",
				IautoConfigDTO.class.getName(), "className");
		digester.addSetProperties("SystemConfig/IautoConfig");
		digester.addSetNext("SystemConfig/IautoConfig", "setIautoConfigDTO",
				IautoConfigDTO.class.getName());

		InputSource inputSource = new InputSource();
		InputStream inputStream = null;
		try {
			inputStream = ResourceConfig.class.getClassLoader()
					.getResourceAsStream(configFile);
			// inputStream = new FileInputStream(new File(resourceUrl.toURI()));
			// inputStream = new
			// FileInputStream(ResourceUtils.getFile(configFile));
			inputSource.setByteStream(inputStream);
			digester.push(this);
			digester.parse(inputSource);
			isInit = true;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			isInit = false;
			throw e;
		} catch (SAXException e) {
			logger.error(e.getMessage());
			isInit = false;
			throw e;
		} finally {
			inputStream.close();
		}
	}

	public boolean isInit() {
		return isInit;
	}

	static {
		instace = new ResourceConfig();
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public ServerConfig getServerConfig() {
		return this.systemConfig.getServerConfig();
	}

	public IautoConfigDTO getIautoConfigDTO() {
		return this.systemConfig.getIautoConfigDTO();
	}

	public String getLocalServer() {
		return localServer;
	}

	public void setLocalServer(String localServer) {
		this.localServer = localServer;
	}

	public static void main(String[] args) throws SAXException, IOException {
		System.out.println(SystemConfig.class.getName());
		ResourceConfig resourceConfig = ResourceConfig.getInstance();
		resourceConfig.init("config/SystemConfig.xml");
	}
}
