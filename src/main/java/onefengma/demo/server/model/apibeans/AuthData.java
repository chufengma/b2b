package onefengma.demo.server.model.apibeans;

import onefengma.demo.common.StringUtils;

/**
 * @author yfchu
 * @date 2016/5/26
 */
public class AuthData {
    private String token;
    private String userId;

    public AuthData(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {
            return false;
        }
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthData authData = (AuthData) o;

        if (token != null ? !token.equals(authData.token) : authData.token != null) return false;
        return userId != null ? userId.equals(authData.userId) : authData.userId == null;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
