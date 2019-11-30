package com.vinsys.app;

import javax.jcr.Repository;
import javax.jcr.SimpleCredentials;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.brixcms.Brix;
import org.brixcms.Path;
import org.brixcms.config.BrixConfig;
import org.brixcms.config.PrefixUriMapper;
import org.brixcms.config.UriMapper;
import org.brixcms.jcr.JcrSessionFactory;
import org.brixcms.jcr.ThreadLocalSessionFactory;
import org.brixcms.markup.web.BrixMarkupNodeWebPage;
import org.brixcms.plugin.site.SitePlugin;
import org.brixcms.util.JcrUtils;
import org.brixcms.web.nodepage.BrixNodePageUrlCodingStrategy;
import org.brixcms.workspace.Workspace;
import org.brixcms.workspace.WorkspaceManager;

public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		// return the Brix --- Home page....
		return BrixNodePageUrlCodingStrategy.HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		Repository repository = JcrUtils.createRepository("file://tmp/brix-jcr1");
		final JcrSessionFactory sf = new ThreadLocalSessionFactory(repository,
				new SimpleCredentials("admin", "admin".toCharArray()));
		final WorkspaceManager wm = JcrUtils.createWorkspaceManager("file://tmp/brix-jcr1", sf);
		UriMapper mapper = new PrefixUriMapper(Path.ROOT) {
			public Workspace getWorkspaceForRequest(WebRequestCycle requestCycle, Brix brix) {
				final String name = "file://tmp/brix-jcr1";
				SitePlugin sitePlugin = SitePlugin.get(brix);
				return sitePlugin.getSiteWorkspace(name, "");
			}
		};
		BrixConfig config = new BrixConfig(sf, wm, mapper);
		config.setHttpPort(8080);
		config.setHttpsPort(8443);

		getDebugSettings().setOutputMarkupContainerClassName(true);
		mount(new QueryStringUrlCodingStrategy("/admin", AdminPage.class));

		// mounting of the default Admin Page
	}

	private JcrSessionFactory getJcrSessionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
