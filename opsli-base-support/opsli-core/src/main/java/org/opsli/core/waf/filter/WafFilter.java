package org.opsli.core.waf.filter;


import org.opsli.core.waf.servlet.WafHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 防火墙
 *
 * @author Parker
 * @date 2020-10-09
 */
public class WafFilter implements Filter {

	private boolean enableXssFilter = false;
	private boolean enableSqlFilter = false;

	private List<String> urlExclusion;


	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String servletPath = httpServletRequest.getServletPath();

		// 如果是排除url 则放行
		if (urlExclusion != null && urlExclusion.contains(servletPath)) {
			chain.doFilter(request, response);
		} else {
			// 执行过滤
			chain.doFilter(
					new WafHttpServletRequestWrapper((HttpServletRequest) request, enableXssFilter, enableSqlFilter),
					response);
		}
	}

	@Override
	public void destroy() {
	}

	// ============================


	public void setEnableXssFilter(boolean enableXssFilter) {
		this.enableXssFilter = enableXssFilter;
	}

	public void setEnableSqlFilter(boolean enableSqlFilter) {
		this.enableSqlFilter = enableSqlFilter;
	}

	public void setUrlExclusion(List<String> urlExclusion) {
		this.urlExclusion = urlExclusion;
	}
}
