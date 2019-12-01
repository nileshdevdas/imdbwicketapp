package com.vinsys.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.util.file.File;
import org.brixcms.Brix;
import org.brixcms.Path;
import org.brixcms.Plugin;
import org.brixcms.auth.Action;
import org.brixcms.auth.AuthorizationStrategy;
import org.brixcms.config.BrixConfig;
import org.brixcms.config.PrefixUriMapper;
import org.brixcms.config.UriMapper;
import org.brixcms.jcr.Jcr2WorkspaceManager;
import org.brixcms.jcr.JcrSessionFactory;
import org.brixcms.jcr.ThreadLocalSessionFactory;
import org.brixcms.jcr.api.JcrSession;
import org.brixcms.plugin.menu.MenuPlugin;
import org.brixcms.plugin.prototype.PrototypePlugin;
import org.brixcms.plugin.site.SitePlugin;
import org.brixcms.plugin.site.page.tile.Tile;
import org.brixcms.plugin.snapshot.SnapshotPlugin;
import org.brixcms.plugin.webdavurl.WebdavUrlPlugin;
import org.brixcms.web.BrixRequestCycleProcessor;
import org.brixcms.web.nodepage.BrixNodePageUrlCodingStrategy;
import org.brixcms.workspace.Workspace;

public class WicketApplication extends WebApplication {
	private Brix brix;

	public Brix getBrix() {
		return brix;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return BrixNodePageUrlCodingStrategy.HomePage.class;
	}

	Repository repository = null;

	@Override
	protected void init() {
		JcrSession session = null;
		try {
			super.init();
			createRepository();
			final JcrSessionFactory sf = sessionFactory();
			Jcr2WorkspaceManager mgr = workspace(sf);
			UriMapper mapper = mapper();
			BrixConfig config = new BrixConfig(sf, mgr, mapper);
			config.setHttpPort(8080);
			config.setHttpsPort(8443);
			brix = initBrix(config);
			brix.attachTo(this);
			SitePlugin sp = SitePlugin.get(brix);
			registerPlugins(config);
			brix.initRepository();
			initWorkspace(session, sp);
			registerTiles(config);
			getDebugSettings().setOutputMarkupContainerClassName(true);
			mount(new QueryStringUrlCodingStrategy("/admin", AdminPage.class));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.logout();
		}
	}

	private void registerTiles(BrixConfig config) {
		config.getRegistry().register(Tile.POINT, new IndexTile());
		config.getRegistry().register(Tile.POINT, new ClockTile());
	}

	private void initWorkspace(JcrSession session, SitePlugin sp) {
		Workspace w = null;
		if (!sp.siteExists("demosite", "")) {
			w = sp.createSite("demosite", "");
			session = brix.getCurrentSession(w.getId());
			brix.initWorkspace(w, session);
			session.save();
		}
	}

	private void registerPlugins(BrixConfig config) {
		config.getRegistry().register(Plugin.POINT, new SnapshotPlugin(brix));
		config.getRegistry().register(Plugin.POINT, new MenuPlugin(brix));
		config.getRegistry().register(Plugin.POINT, new PrototypePlugin(brix));
		config.getRegistry().register(Plugin.POINT, new WebdavUrlPlugin());
	}

	private Brix initBrix(BrixConfig config) {
		return new Brix(config) {
			@Override
			public AuthorizationStrategy newAuthorizationStrategy() {
				class DefaultAuthStrategy implements AuthorizationStrategy {
					@Override
					public boolean isActionAuthorized(Action action) {
						return true;
					}
				}
				return new DefaultAuthStrategy();
			}
		};
	}

	private UriMapper mapper() {
		UriMapper mapper = new PrefixUriMapper(Path.ROOT) {
			public Workspace getWorkspaceForRequest(WebRequestCycle requestCycle, Brix brix) {
				final String name = "demosite";
				SitePlugin sitePlugin = SitePlugin.get(brix);
				return sitePlugin.getSiteWorkspace(name, "");
			}
		};
		return mapper;
	}

	private Jcr2WorkspaceManager workspace(final JcrSessionFactory sf) {
		Jcr2WorkspaceManager mgr = new Jcr2WorkspaceManager(sf);
		mgr.initialize();
		return mgr;
	}

	private void createRepository() throws FileNotFoundException, ConfigurationException, RepositoryException {
		File home = new File("C:/jcrdemo/");
		InputStream configStream = new FileInputStream(home.getAbsoluteFile() + "/" + "repository.xml");
		RepositoryConfig repoconfig = RepositoryConfig.create(configStream, home.getAbsolutePath());
		repository = RepositoryImpl.create(repoconfig);
	}

	private JcrSessionFactory sessionFactory() {
		final JcrSessionFactory sf = new ThreadLocalSessionFactory(repository,
				new SimpleCredentials("admin", "admin".toCharArray()));
		return sf;
	}

	protected IRequestCycleProcessor newRequestCycleProcessor() {
		return new BrixRequestCycleProcessor(brix);
	}

	@Override
	protected void onDestroy() {
		if (repository != null) {
			if (repository instanceof RepositoryImpl) {
				((RepositoryImpl) repository).shutdown();
			}
		}
		super.onDestroy();
	}
}
