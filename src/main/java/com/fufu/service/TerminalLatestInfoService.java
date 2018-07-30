package com.fufu.service;

import com.fufu.entity.vo.TerminalPositionReqVo;
import com.fufu.entity.vo.TerminalPositionRspVo;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author fufu
 * @version 0.1.0
 * @date 2018-07-30
 */
public interface TerminalLatestInfoService {

    void setPosition(TerminalPositionReqVo terminalPositionReqVo);

    List<TerminalPositionRspVo> getTerminalByRange(BigDecimal longitude, BigDecimal latitude, int radius);

}
