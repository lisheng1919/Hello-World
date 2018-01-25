--**************************************************
-- Name:        p_imageinfo
-- Purpose:     Ʊ��ͼ����Ϣ��
-- Deviser:     ���
-- Devistime:   2015-04-30
--**************************************************
create table p_imageinfo 
(
   ID				VARCHAR(20)    ,             
   -- ID
   ReleaseTime			date	       ,             
   -- ����ʱ��
   MedAcctNo			VARCHAR(20)    ,
   -- �����˺�
   AcctNo			VARCHAR(20)    ,
   -- �ͻ��˺�
   cellPhone			VARCHAR(20)    ,
   -- �ֻ���
   SystemId			VARCHAR(20)    ,	
   -- ����ϵͳ��
   CntId			VARCHAR(20)    ,
   -- ��Ա��
   CompanyCode			VARCHAR(20)    ,
   -- �����
   JJID				VARCHAR(20)    ,
   -- ����ID
   QJBS				VARCHAR(2)    ,
   -- �����ʶ Ĭ��0
   QJUSER			VARCHAR(20)    ,
   -- �����
   ElectType			VARCHAR(5)     ,
   -- ҵ������
   ButtonType			VARCHAR(50)    ,
   -- ��ť����
   TRANTYPE			VARCHAR(2)    ,
   -- Ʊ������
   FrontImage			VARCHAR(100)    ,
   -- ����ͼ��
   FrontImagePath		VARCHAR(50)    ,
   -- ����ͼ��洢·��
   BackImage			VARCHAR(100)    ,
   -- ����ͼ��
   BackImagePath		VARCHAR(50)    ,
   -- ����ͼ��洢·��
   VoucherImage			VARCHAR(100)    ,
   -- ƾ֤��ͼ��
   VoucherImagePath		VARCHAR(50)    ,
   -- ƾ֤��ͼ��洢·��
   DataSendState		VARCHAR(5)     ,
   -- ��������״̬ 0=δ����,1=������
   DataSendRemark		VARCHAR(3000)    ,
   -- �������ͱ�ע
   IamgeSendState		VARCHAR(5)    ,
   -- CPӰ���ϴ�״̬ 0=δ���ͣ�1=������
   IamgeSendRemark		VARCHAR(50)    ,
   -- CPӰ���ϴ���ע
   TIamgeSendState		VARCHAR(5)    ,
   -- ҵ��Ӱ���ϴ�״̬ 0=δ���ͣ�1=������
   TIamgeSendRemark		VARCHAR(50)    ,
   -- ҵ��Ӱ���ϴ���ע
   fileType			VARCHAR(50)    ,
   -- Ӱ���׺�
   TranUid			VARCHAR(36)    ,
   -- ������ˮ��
   FlwCommonId			VARCHAR(35)    ,
   -- ǰ��̨�����������ˮ��
   ScanSeqNo			VARCHAR(35)    ,
   -- ǰ��̨����ɨ����ˮ��
   billNoRet			VARCHAR(20)    ,
   -- Ʊ�ݺ���
   BillState			VARCHAR(10)    ,
   -- Ʊ������״̬
   BillTime			VARCHAR(20)    ,
   -- Ʊ����������

   constraint PK_p_imageinfo primary key (ID)
);
comment on column p_imageinfo.ID is 'ID';
comment on column p_imageinfo.ReleaseTime is '����ʱ��';
comment on column p_imageinfo.MedAcctNo	is '�����˺�';
comment on column p_imageinfo.AcctNo	is '�ͻ��˺�';
comment on column p_imageinfo.cellPhone	is '�ֻ���';
comment on column p_imageinfo.SystemId	is '����ϵͳ��';
comment on column p_imageinfo.CntId is '��Ա��';
comment on column p_imageinfo.CompanyCode is '�����';
comment on column p_imageinfo.JJID is '����ID';
comment on column p_imageinfo.QJBS is '�����ʶ';
comment on column P_IMAGEINFO.QJUSER is '����û�';
comment on column p_imageinfo.ElectType is 'ҵ������';
comment on column p_imageinfo.ButtonType is '��ť����';
comment on column p_imageinfo.TRANTYPE is 'Ʊ������';
comment on column p_imageinfo.FrontImage is '����ͼ��';
comment on column p_imageinfo.FrontImagePath is '����ͼ��洢·��';
comment on column p_imageinfo.BackImage is '����ͼ��';
comment on column p_imageinfo.BackImagePath is '����ͼ��洢·��';
comment on column p_imageinfo.VoucherImage is 'ƾ֤��ͼ��';
comment on column p_imageinfo.VoucherImagePath is 'ƾ֤��ͼ��洢·��';
comment on column p_imageinfo.DataSendState is '��������״̬';
comment on column p_imageinfo.DataSendRemark is '�������ͱ�ע';
comment on column p_imageinfo.IamgeSendState is 'CPӰ������״̬';
comment on column p_imageinfo.IamgeSendRemark is 'CPӰ�����ͱ�ע';
comment on column p_imageinfo.TIamgeSendState is 'ҵ��Ӱ���ϴ�״̬';
comment on column p_imageinfo.TIamgeSendRemark is 'ҵ��Ӱ���ϴ���ע';
comment on column p_imageinfo.fileType is 'Ӱ���׺�';
comment on column p_imageinfo.TranUid is '������ˮ��';
comment on column p_imageinfo.FlwCommonId is 'ǰ��̨�����������ˮ��';
comment on column p_imageinfo.ScanSeqNo is 'ǰ��̨����ɨ����ˮ��';
comment on column p_imageinfo.billNoRet is 'Ʊ�ݺ���';
comment on column p_imageinfo.BillState is 'Ʊ������״̬';
comment on column p_imageinfo.BillTime is 'Ʊ����������';


--**************************************************
-- Name:        p_accinfo
-- Purpose:     �˻���Ϣ��
-- Deviser:     ���
-- Devistime:   2015-04-30
--**************************************************
create table p_accinfo 
(
   MedAcctNo			VARCHAR(20)    ,
   -- �����˺�
   AcctName			VARCHAR(70)    ,	
   -- �û���
   cellPhone			VARCHAR(20)    ,
   -- �ֻ���

   constraint PK_p_accinfo primary key (MedAcctNo)
);
comment on column p_accinfo.MedAcctNo is '�����˺�';
comment on column p_accinfo.AcctName is '�û���';
comment on column p_accinfo.cellPhone	is '�ֻ���';

-- Ʊ�ݱ�seq
create sequence imageinfoseq
minvalue 1000000001
maxvalue 9999999999
start with 1000000001
increment by 1;
--**************************************************
-- Name:        p_businessid
-- Purpose:     ҵ����������
-- Deviser:     ���
-- Devistime:   2015-07-05
--**************************************************
create table p_businessid 
(
   ID				VARCHAR(20)    ,             
   -- ҵ����ˮ��
   constraint PK_p_businessid primary key (ID)
);
comment on column p_businessid.ID is 'ҵ����ˮ��';

--�û�����������
alter table P_ACCINFO modify ACCTNAME VARCHAR2(200);


--**************************************************
-- Name:        p_ocxversion
-- Purpose:     �ؼ��汾��Ϣ��
-- Deviser:     ���
-- Devistime:   2015-09-07
--**************************************************
create table p_ocxversion 
(
   EquipmentType		VARCHAR(10)    ,
   -- ��������
   VersionCode			VARCHAR(10)    ,	
   -- �汾��

   constraint PK_p_ocxversion primary key (EquipmentType)
);
comment on column p_ocxversion.EquipmentType is '��������';
comment on column p_ocxversion.VersionCode   is '�汾��';