package onefengma.demo.server.core.request;

/**
 * @author yfchu
 * @date 2016/5/26
 */
public class ParamsMissException extends Exception {

    public ParamsMissException(String params) {
        super("params " + params + " is required!");
    }
}
