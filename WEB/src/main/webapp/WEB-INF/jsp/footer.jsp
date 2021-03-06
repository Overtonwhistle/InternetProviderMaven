<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:requestEncoding value="utf-8" />
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" />
<fmt:message key="local.made_by" var="made_by" />
<!-- FOOTER -->
<div class="footer">
	<div class="footer_row_of_three">
		<div class="footer_one_of_three">
			<a href='https://www.facebook.com/EPAM.Belarus' title='Facebook: EPAM Belarus' target='Social networks'>
				<svg width="32" height="32" viewBox="0 0 32 32">
            <path fill="#bbc3c1"
						d="M16 0c-8.837 0-16 7.163-16 16s7.163 16 16 16v-12h-4v-4h4v-3c0-2.761 2.239-5 5-5h5v4h-5c-0.552
               0-1 0.448-1 1v3h5.5l-1 4h-4.5v11.496c6.901-1.776 12-8.041 12-15.496 0-8.837-7.163-16-16-16z">
            </path>
            </svg>
			</a> <a href='https://twitter.com/epam_minsk' title='Twitter: EPAM Belarus' target='Social networks'> <svg
					viewBox="0 0 32 32">
            <path fill="#bbc3c1"
						d="M16 0c-8.837 0-16 7.163-16 16s7.163 16 16 16 16-7.163 16-16-7.163-16-16-16zM23.952 11.921c0.008
               0.176 0.012 0.353 0.012 0.531 0 5.422-4.127 11.675-11.675 11.675-2.317 0-4.474-0.679-6.29-1.844
                0.321 0.038 0.648 0.058 0.979 0.058 1.922 0 3.692-0.656 5.096-1.757-1.796-0.033-3.311-1.219-3.833-2.849
                 0.251 0.048 0.508 0.074 0.772 0.074 0.374 0 0.737-0.050 1.081-0.144-1.877-0.377-3.291-2.035-3.291-4.023
                  0-0.017 0-0.034 0-0.052 0.553 0.307 1.186 0.492 1.858 0.513-1.101-0.736-1.825-1.992-1.825-3.415 0-0.752
                   0.202-1.457 0.556-2.063 2.024 2.482 5.047 4.116 8.457 4.287-0.070-0.3-0.106-0.614-0.106-0.935 0-2.266
                    1.837-4.103 4.103-4.103 1.18 0 2.247 0.498 2.995 1.296 0.935-0.184 1.813-0.525 2.606-0.996-0.306
                     0.958-0.957 1.762-1.804 2.27 0.83-0.099 1.621-0.32 2.357-0.646-0.55 0.823-1.245 1.545-2.047 2.124z">
            </path>
        </svg>
			</a>
		</div>
		<div class="footer_one_of_three">
			<div>&copy; 2017 Your Internet Provider</div>
		</div>
		<div class="footer_one_of_three">${made_by}<a href='mailto:overton.whistle@gmail.com'
				title='click to mail me'> Pavel Sorokoletov</a>
		</div>
	</div>
</div>
