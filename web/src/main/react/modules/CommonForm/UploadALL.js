import React from 'react';
import antd from 'antd';  
var Select = antd.Select;
var Option = Select.Option;
var Modal = antd.Modal;    
var UploadItem = require('./UploadItem');  
const NavLine = require("../utils/NavLine");
export default React.createClass({
 
  getInitialState() { 
     return {
       };
   },  
  /*navLineData2:{
      "身份证":"#u1",
      "户口本":"#u3",
      "婚姻证明":"#u4",
      "征信资料":"#u5",
      "建委":"#u6",
      "人法":"#u7",
      "同盾":"#u8",
      "安融":"#u9",
      "工商网":"#u10",
      "配偶":"#u11",
      "共借人":"#u12",
      "查档说明":"#u13",
      "房产资料":"#u14",
      "世联":"#u15",
      "仁达":"#u16",
      "链家":"#u17",
      "中介名片":"#u18",
      "下户照片":"#u19",
      "定位截屏":"#u20",
      "租赁合同":"#u21",
      "购房合同":"#u22",
      "放弃优先购买权协议":"#u23",
      "核行资料（银行贷款资料）":"#u24",
      "风控单":"#u25",
      "签合同照片":"#u26",
      "合同资料":"#u27",
      "公证回执单":"#u28",
      "公证材料":"#u29",
      "抵押回执单":"#u30",
      "放款卡":"#u31",
      "三方卡":"#u32",
      "他项权利证材料":"#u33",
      "展期协议":"#u34",
      "征信报告":"#u35"
  },*/
  navLineData:{
      "基本信息":"#H1",
      "筛查信息":"#H2",
      "房产信息":"#H3",
      "下户寻值信息":"#H4",
      "核行信息":"#H5",
      "面审信息":"#H6",
      "合同信息":"#H7",
      "公证登记":"#H8",
      "抵押登记":"#H9",
      "展期信息":"#H10"
  },
  render() {    
    var props = this.props; 
    var selectRecord = props.selectRecord;
    var idData = props.idData;
    var otherType = props.otherType;
    var canEdit = props.canEdit; 
    return(
      <div style={{position: "relative"}}>
        <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
          <div className="col-20 navLine-wrap-left" >
              <h1 id="H1">基本信息</h1>
              <div id="u1" >
                <h2>身份证</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"IDCARD"} canEdit={canEdit}/>
              </div> 
              <div id="u3" >
                <h2>户口本</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"HOUSEHOLD"} canEdit={canEdit}/>
              </div>
              <div id="u4" >
                <h2>婚姻证明</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"MARRIAGE"} canEdit={canEdit}/>
              </div> 
              <div id="u5" >
                <h2>征信资料</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"CREDIT"} canEdit={canEdit}/>
              </div>
              <h1 id="H2">筛查信息</h1> 
              <div id="u6" >
                <h2>建委</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"queryOrg1"} canEdit={canEdit}/>
              </div> 
              <div id="u7" >
                <h2>人法</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"queryOrg2"} canEdit={canEdit}/>
              </div> 
              <div id="u8" >
                <h2>同盾</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"queryOrg3"} canEdit={canEdit}/>
              </div> 
              <div id="u9" >
                <h2>安融</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"queryOrg4"} canEdit={canEdit}/>
              </div> 
              <div id="u10" >
                <h2>工商网</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"queryOrg5"} canEdit={canEdit}/>
              </div> 
              <div id="u11" >
                <h2>配偶</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"SPOUSE"} canEdit={canEdit}/>
              </div> 
              <div id="u12" >
                <h2>共借人</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"WEREBORROWED"} canEdit={canEdit}/>
              </div> 
              <div id="u13" >
                <h2>查档说明</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"WHTHIN"} canEdit={canEdit}/>
              </div>
              <h1 id="H3">房产信息</h1>  
              <div id="u14" >
                <h2>房产资料</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} otherType={otherType} idData={idData} btype ={"HOUSE"} canEdit={canEdit}/>
              </div> 
              <div id="u15" >
                <h2>世联</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"evaOrg1"} canEdit={canEdit}/>
              </div> 
              <div id="u16" >
                <h2>仁达</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"evaOrg3"} canEdit={canEdit}/>
              </div> 
              <div id="u17" >
                <h2>链家</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"evaOrg2"} canEdit={canEdit}/>
              </div>
              <h1 id="H4">下户寻值信息</h1> 
              <div id="u18" >
                <h2>中介名片</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"REALTOR"} canEdit={canEdit}/>
              </div>
              <div id="u19" >
                <h2>下户照片</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"NEXTDOOR"} canEdit={canEdit}/>
              </div> 
              <div id="u20" >
                <h2>定位截屏</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"JIEPING"} canEdit={canEdit}/>
              </div> 
              <div id="u23" >
                <h2>放弃优先购买权协议</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"GiveUp"} canEdit={canEdit}/>
              </div>
              <h1 id="H5">核行信息</h1> 
              <div id="u24" >
                <h2>核行资料（银行贷款资料）</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"QUICKLY"} canEdit={canEdit}/>
              </div> 
              <h1 id="H6">面审信息</h1> 
              <div id="u25" >
                <h2>风控单</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} otherType={otherType} idData={idData} btype ={"RISK"} canEdit={canEdit}/>
              </div> 
              <h1 id="H7">合同信息</h1> 
              <div id="u27" >
                <h2>合同与签合同照片</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"Contract_Pic"} canEdit={canEdit}/>
              </div>
              <h1 id="H8">公证登记</h1> 
              <div id="u28" >
                <h2>公证回执单</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"HUIZHIDAN"} canEdit={canEdit}/>
              </div> 
              <div id="u29" >
                <h2>公证材料</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"JUSTICE"} canEdit={canEdit}/>
              </div>
              <h1 id="H9">抵押登记</h1> 
              <div id="u30" >
                <h2>抵押回执单</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"MortgageReceipt"} canEdit={canEdit}/>
              </div>
              <div id="u31" >
                <h2>放款卡</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"CardNumber"} canEdit={canEdit}/>
              </div> 
              <div id="u32" >
                <h2>三方卡</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"ThirdCardNumber"} canEdit={canEdit}/>
              </div>
              <div id="u33" >
                <h2>他项权利证材料</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"MATERIAL"} canEdit={canEdit}/>
              </div>
              <h1 id="H10">展期信息</h1> 
              <div id="u34" >
                <h2>展期协议</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"RenewalAgreement"} canEdit={canEdit}/>
              </div> 
              <div id="u35" >
                <h2>征信报告</h2>
                <UploadItem selectRecord={selectRecord} otherType={otherType} idData={idData} btype ={"CreditReport"} canEdit={canEdit}/>
              </div>   
          </div>
          <div className="navLine-wrap-right" >
            <NavLine navLineData={this.navLineData} activeItem="#u1" ref="NavLine"/>
          </div>
         </div>
      </div>
    )
  }
});