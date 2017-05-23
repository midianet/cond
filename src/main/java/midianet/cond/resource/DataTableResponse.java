package midianet.cond.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name=DataTableResponse.SCHEMA_NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DataTableResponse.SCHEMA_NAME,propOrder = {})
public class DataTableResponse {
    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List data;
    private String error;

    public void setDraw(final Integer draw) {
        this.draw = draw;
    }

    public void setRecordsTotal(final Integer total) {
        this.recordsTotal = total;
    }

    public void setRecordsFiltered(final Integer filtered) {
        this.recordsFiltered = filtered;
    }

    public void setData(final List data) {
        this.data = data;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Integer getDraw() {
        return draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public List getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public final static String SCHEMA_NAME = "datatableresponse";

    public final static String JSON ="application/midianet.representation."+SCHEMA_NAME+"+json";

}