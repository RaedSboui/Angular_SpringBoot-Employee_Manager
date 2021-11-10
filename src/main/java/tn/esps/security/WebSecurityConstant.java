package tn.esps.security;

public class WebSecurityConstant {
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "jwt-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verifies";
	public static final String EMPLOYEE_MANAGER_LLC = "Employee Manager LLC";
	public static final String EMPLOYEE_MANAGER_ADMINISTRATION = "User Managment Portal";
	public static final String AUTHOROTIES = "Authorities";
	public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
	public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
	public static final String OPTIONS_HTTP_METHOD = "Options";
	public static final String[] PUBLIC_URLS = { "/api/auth/login" };
	public static final String CSRF_IGNORE = "/api/auth/login";
}
