--**************************************************
-- Name:        p_imageinfo
-- Purpose:     票据图像信息表
-- Deviser:     李根
-- Devistime:   2015-04-30
--**************************************************
create table p_imageinfo 
(
   ID				VARCHAR(20)    ,             
   -- ID
   ReleaseTime			date	       ,             
   -- 发起时间
   MedAcctNo			VARCHAR(20)    ,
   -- 介质账号
   AcctNo			VARCHAR(20)    ,
   -- 客户账号
   cellPhone			VARCHAR(20)    ,
   -- 手机号
   SystemId			VARCHAR(20)    ,	
   -- 发起系统号
   CntId			VARCHAR(20)    ,
   -- 柜员号
   CompanyCode			VARCHAR(20)    ,
   -- 网点号
   JJID				VARCHAR(20)    ,
   -- 机具ID
   QJBS				VARCHAR(2)    ,
   -- 清机标识 默认0
   QJUSER			VARCHAR(20)    ,
   -- 清机人
   ElectType			VARCHAR(5)     ,
   -- 业务类型
   ButtonType			VARCHAR(50)    ,
   -- 按钮类型
   TRANTYPE			VARCHAR(2)    ,
   -- 票据类型
   FrontImage			VARCHAR(100)    ,
   -- 正面图像
   FrontImagePath		VARCHAR(50)    ,
   -- 正面图像存储路径
   BackImage			VARCHAR(100)    ,
   -- 反面图像
   BackImagePath		VARCHAR(50)    ,
   -- 反面图像存储路径
   VoucherImage			VARCHAR(100)    ,
   -- 凭证号图像
   VoucherImagePath		VARCHAR(50)    ,
   -- 凭证号图像存储路径
   DataSendState		VARCHAR(5)     ,
   -- 数据上送状态 0=未上送,1=已上送
   DataSendRemark		VARCHAR(3000)    ,
   -- 数据上送备注
   IamgeSendState		VARCHAR(5)    ,
   -- CP影像上传状态 0=未上送，1=已上送
   IamgeSendRemark		VARCHAR(50)    ,
   -- CP影像上传备注
   TIamgeSendState		VARCHAR(5)    ,
   -- 业务影像上传状态 0=未上送，1=已上送
   TIamgeSendRemark		VARCHAR(50)    ,
   -- 业务影像上传备注
   fileType			VARCHAR(50)    ,
   -- 影像套号
   TranUid			VARCHAR(36)    ,
   -- 请求流水号
   FlwCommonId			VARCHAR(35)    ,
   -- 前后台分离第三方流水号
   ScanSeqNo			VARCHAR(35)    ,
   -- 前后台分离扫描流水号
   billNoRet			VARCHAR(20)    ,
   -- 票据号码
   BillState			VARCHAR(10)    ,
   -- 票据受理状态
   BillTime			VARCHAR(20)    ,
   -- 票据受理日期

   constraint PK_p_imageinfo primary key (ID)
);
comment on column p_imageinfo.ID is 'ID';
comment on column p_imageinfo.ReleaseTime is '发起时间';
comment on column p_imageinfo.MedAcctNo	is '介质账号';
comment on column p_imageinfo.AcctNo	is '客户账号';
comment on column p_imageinfo.cellPhone	is '手机号';
comment on column p_imageinfo.SystemId	is '发起系统号';
comment on column p_imageinfo.CntId is '柜员号';
comment on column p_imageinfo.CompanyCode is '网点号';
comment on column p_imageinfo.JJID is '机具ID';
comment on column p_imageinfo.QJBS is '清机标识';
comment on column P_IMAGEINFO.QJUSER is '清机用户';
comment on column p_imageinfo.ElectType is '业务类型';
comment on column p_imageinfo.ButtonType is '按钮类型';
comment on column p_imageinfo.TRANTYPE is '票据类型';
comment on column p_imageinfo.FrontImage is '正面图像';
comment on column p_imageinfo.FrontImagePath is '正面图像存储路径';
comment on column p_imageinfo.BackImage is '反面图像';
comment on column p_imageinfo.BackImagePath is '反面图像存储路径';
comment on column p_imageinfo.VoucherImage is '凭证号图像';
comment on column p_imageinfo.VoucherImagePath is '凭证号图像存储路径';
comment on column p_imageinfo.DataSendState is '数据上送状态';
comment on column p_imageinfo.DataSendRemark is '数据上送备注';
comment on column p_imageinfo.IamgeSendState is 'CP影像上送状态';
comment on column p_imageinfo.IamgeSendRemark is 'CP影像上送备注';
comment on column p_imageinfo.TIamgeSendState is '业务影像上传状态';
comment on column p_imageinfo.TIamgeSendRemark is '业务影像上传备注';
comment on column p_imageinfo.fileType is '影像套号';
comment on column p_imageinfo.TranUid is '请求流水号';
comment on column p_imageinfo.FlwCommonId is '前后台分离第三方流水号';
comment on column p_imageinfo.ScanSeqNo is '前后台分离扫描流水号';
comment on column p_imageinfo.billNoRet is '票据号码';
comment on column p_imageinfo.BillState is '票据受理状态';
comment on column p_imageinfo.BillTime is '票据受理日期';


--**************************************************
-- Name:        p_accinfo
-- Purpose:     账户信息表
-- Deviser:     李根
-- Devistime:   2015-04-30
--**************************************************
create table p_accinfo 
(
   MedAcctNo			VARCHAR(20)    ,
   -- 介质账号
   AcctName			VARCHAR(70)    ,	
   -- 用户名
   cellPhone			VARCHAR(20)    ,
   -- 手机号

   constraint PK_p_accinfo primary key (MedAcctNo)
);
comment on column p_accinfo.MedAcctNo is '介质账号';
comment on column p_accinfo.AcctName is '用户名';
comment on column p_accinfo.cellPhone	is '手机号';

-- 票据表seq
create sequence imageinfoseq
minvalue 1000000001
maxvalue 9999999999
start with 1000000001
increment by 1;
--**************************************************
-- Name:        p_businessid
-- Purpose:     业务办理情况表
-- Deviser:     李根
-- Devistime:   2015-07-05
--**************************************************
create table p_businessid 
(
   ID				VARCHAR(20)    ,             
   -- 业务流水号
   constraint PK_p_businessid primary key (ID)
);
comment on column p_businessid.ID is '业务流水号';

--用户名长度增加
alter table P_ACCINFO modify ACCTNAME VARCHAR2(200);


--**************************************************
-- Name:        p_ocxversion
-- Purpose:     控件版本信息表
-- Deviser:     李根
-- Devistime:   2015-09-07
--**************************************************
create table p_ocxversion 
(
   EquipmentType		VARCHAR(10)    ,
   -- 机具类型
   VersionCode			VARCHAR(10)    ,	
   -- 版本号

   constraint PK_p_ocxversion primary key (EquipmentType)
);
comment on column p_ocxversion.EquipmentType is '机具类型';
comment on column p_ocxversion.VersionCode   is '版本号';