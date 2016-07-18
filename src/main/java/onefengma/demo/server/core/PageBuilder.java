package onefengma.demo.server.core;

import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.model.apibeans.BasePageBean;

/**
 * Created by chufengma on 16/6/5.
 */
public class PageBuilder {
    public int currentPage;
    public int pageCount;

    public long startTime = -1;
    public long endTime = -1;

    public String keyword;

    List<OrderBy> orderByList = new ArrayList();
    List<Where> wereList = new ArrayList<>();

    public PageBuilder setTime(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        return this;
    }

    public static class OrderBy {
        String name;
        String order;

        public OrderBy(String name, boolean desc) {
            this.name = name;
            this.order = ((desc) ? "desc" : "asc");
        }

        public String generate() {
            return name + " " + order;
        }
    }

    public static class Where {
        String key;
        Object value;

        public Where(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String generate() {
            if (value instanceof String) {
                return " " + key + " = '" + value + "' ";
            } else {
                return " " + key + " = " + value + " ";
            }
        }
    }

    public static class InWhere extends Where {
        public InWhere(String key, List<String> value) {
            super(key, value);
        }

        @Override
        public String generate() {
            StringBuilder stringBuilder = new StringBuilder(" " + key + " in (");
            List<String> vas = (List<String>) value;
            for (int i = 0; i < vas.size(); i++) {
                String va = vas.get(i);
                stringBuilder.append("'" + va + "'");
                if (i != vas.size() - 1) {
                    stringBuilder.append(",");
                }

            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    public static class InWhereNumber extends Where {

        public InWhereNumber(String key, List<Integer> value) {
            super(key, value);
        }

        @Override
        public String generate() {
            StringBuilder stringBuilder = new StringBuilder(" " + key + " in (");
            List<Integer> vas = (List<Integer>) value;
            for (int i = 0; i < vas.size(); i++) {
                int va = vas.get(i);
                stringBuilder.append(va + "");
                if (i != vas.size() - 1) {
                    stringBuilder.append(",");
                }

            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    public PageBuilder(int currentPage, int pageCount) {
        this.currentPage = currentPage;
        this.pageCount = pageCount;
        if (pageCount <= 0 || pageCount >= 300) {
            this.pageCount = 300;
        }
    }

    public PageBuilder addEqualWhere(String key, Object value) {
        if (value == null) {
            return this;
        }
        if (value instanceof String && StringUtils.isEmpty((String) value)) {
            return this;
        }
        if (value instanceof Integer && (int) value == -1) {
            return this;
        }
        if (value == null) {
            return null;
        }
        this.wereList.add(new Where(key, value));
        return this;
    }

    public PageBuilder addEqualWhereCanEmpty(String key, String value) {
        if (value == null) {
            return this;
        }
        this.wereList.add(new Where(key, value));
        return this;
    }


    public PageBuilder addInWhere(String key, List<String> value) {
        if (value == null || value.isEmpty()) {
            return this;
        }
        this.wereList.add(new InWhere(key, value));
        return this;
    }

    public PageBuilder addInWhereNumber(String key, List<Integer> value) {
        if (value == null || value.isEmpty()) {
            return this;
        }
        this.wereList.add(new InWhereNumber(key, value));
        return this;
    }

    public PageBuilder setOrderByRequest(BasePageBean basePageBean) {
        if (!StringUtils.isEmpty(basePageBean.keyword)) {
            this.keyword = basePageBean.keyword;
        }
        if (!StringUtils.isEmpty(basePageBean.monthSellCount)) {
            return orderByMonthSales(Boolean.parseBoolean(basePageBean.monthSellCount));
        } else if (!StringUtils.isEmpty(basePageBean.price)) {
            return orderByPrice(Boolean.parseBoolean(basePageBean.price));
        } else if (!StringUtils.isEmpty(basePageBean.score)) {
            return orderByScore(Boolean.parseBoolean(basePageBean.score));
        } else if (!StringUtils.isEmpty(basePageBean.productCount)) {
            addOrderBy("productCount", Boolean.parseBoolean(basePageBean.productCount));
        } else if (!StringUtils.isEmpty(basePageBean.monthSellMoney)) {
            orderByList.add(new OrderBy("money", Boolean.parseBoolean(basePageBean.monthSellMoney)));
            return this;
        }
        return this;
    }

    public PageBuilder addOrderBy(String key, boolean desc) {
        orderByList.add(new OrderBy(key, desc));
        return this;
    }

    public PageBuilder orderByTime(long start, long end) {
        this.startTime = start;
        this.endTime = end;
        return this;
    }

    public PageBuilder orderByMonthSales(boolean desc) {
        orderByList.add(new OrderBy("monthSellCount", desc));
        return this;
    }

    public PageBuilder orderByPrice(boolean desc) {
        orderByList.add(new OrderBy("price", desc));
        return this;
    }

    public PageBuilder orderByScore(boolean desc) {
        orderByList.add(new OrderBy("score", desc));
        return this;
    }

    public boolean hasWhere() {
        return !wereList.isEmpty();
    }

    public String generateSql(boolean withLimit) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Where where : wereList) {
            stringBuffer.append(where.generate());
            stringBuffer.append(" and");
        }
        if (startTime != -1 && endTime != -1) {
            stringBuffer.append(" pushTime <=" + endTime + " and pushTime >=" + startTime);
        } else {
            if (!wereList.isEmpty()) {
                stringBuffer.delete(stringBuffer.length() - 3, stringBuffer.length());
            }
        }
        if (!orderByList.isEmpty()) {
            stringBuffer.append(" order by ");
        }
        for (OrderBy orderBy : orderByList) {
            stringBuffer.append(" " + orderBy.generate() + " ");
            stringBuffer.append(",");
        }
        if (stringBuffer.toString().endsWith(",")) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        if (withLimit) {
            stringBuffer.append(" limit " + currentPage * pageCount + ", " + pageCount);
        }
        return stringBuffer.toString();
    }

    public String generateWhere() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < wereList.size(); i++) {
            Where where = wereList.get(i);
            stringBuilder.append(where.generate());
            if (i != wereList.size() - 1) {
                stringBuilder.append(" and");
            }
        }
        return stringBuilder.toString();
    }

    public String generateLimit() {
        return " limit " + currentPage * pageCount + ", " + pageCount;
    }
}
