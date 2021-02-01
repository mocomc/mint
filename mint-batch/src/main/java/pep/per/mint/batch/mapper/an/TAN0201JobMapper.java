package pep.per.mint.batch.mapper.an;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.batch.TransactionLogScaleInfo;



public interface TAN0201JobMapper {

    public List<TransactionLogScaleInfo> getTxLogScaleInfoList(Map params) throws Exception;

    public int updateTxLogScaleInfo(TransactionLogScaleInfo info) throws Exception;

}
