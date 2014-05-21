package com.jinnova.smartpad.android;

/**
 * constants are identically copied from IDetailManager
 *
 */
public interface ServerConstants {

	static final String REST_SCHEME = "smartpad://";
	static final String REST_FEEDS = "feeds";
	static final String REST_DRILL = "drill";

	static final String VERSIONING_TARGET = "VT";
	static final String VERSIONING_NEW = "VN";

	static final String FIELD_ACTION_LOADNEXT = "ALN";
	static final String FIELD_VERSION = "V";
	static final String FIELD_TYPE = "T";
	static final String FIELD_TYPENUM = "TN";
	static final String FIELD_ARRAY = "A";
	static final String FIELD_PAGE = "P";
	static final String FIELD_SIZE = "S";
	static final String FIELD_ID = "ID";
	static final String FIELD_SYSCATID = "SCI";
	static final String FIELD_SYSCATNAME = "SCN";
	static final String FIELD_NAME = "N";
	static final String FIELD_DESC = "D";
	static final String FIELD_IMAGE = "IMG";
	static final String FIELD_BRANCHID = "BI";
	static final String FIELD_BRANCHNAME = "BN";
	static final String FIELD_CATID = "CID";
	static final String FIELD_CATNAME = "CN";
	static final String FIELD_UP_ID = "UI";
	static final String FIELD_UP_NAME = "UN";
	static final String FIELD_DISP = "DISP";

	static final String FIELD_SEGMENT = "SEG";
	static final String FIELD_SEGMENT_REMOVER = "SEG_DEL";
	static final String FIELD_SEGMENT_FIELDID = "SFID";
	static final String FIELD_SEGMENT_FIELDNAME = "SFN";
	static final String FIELD_SEGMENT_VALUE = "SV";
	static final String FIELD_SEGMENT_VALUEID = "SVID";
	static final String FIELD_SEGMENT_LINK = "SL";
	
	static String SYSTEM_BRANCH_ID = "smartpad";
	static String SYSTEM_CAT_ALL = "z";
	static String CLUSPRE = "z";

	static int TYPE_UNKNOWN = -1;
	//static int TYPE_NO = 0;
	static int TYPE_BRANCH = 1;
	static int TYPE_STORE = 2;
	static int TYPE_CAT = 3;
	static int TYPE_CATITEM = 4;
	static int TYPE_PROMO = 5;
	static int TYPE_SYSCAT = 6;
	static int TYPE_POST = 7;
	static int TYPE_COMPOUND = 8;
	static int TYPE_COUNT = 9;

	static String TYPENAME_NO = null;
	static String TYPENAME_BRANCH = "branch";
	static String TYPENAME_STORE = "store";
	static String TYPENAME_CAT = "cat";
	static String TYPENAME_CATITEM = "citem";
	//static String TYPENAME_CATITEM_WITHBRANCH = "citem_b";
	static String TYPENAME_PROMO = "pro";
	static String TYPENAME_POST = "post";
	static String TYPENAME_SYSCAT = "syscat";
	static String TYPENAME_SEGMENT = "segment";
	
	//static String[] TYPENAMES = new String[] {TYPENAME_NO, TYPENAME_BRANCH, TYPENAME_STORE, TYPENAME_CAT, TYPENAME_CATITEM, TYPENAME_PROMO};
	//RULE: Singular type name + s
	static String TYPENAME_COMPOUND = "misc";
	static String TYPENAME_COMPOUND_BRANCHSTORE = "branchstore";
	static String TYPENAME_COMPOUND_CAT = "cats";
	static String TYPENAME_COMPOUND_CITEM = "citems";
	static String TYPENAME_COMPOUND_PROMOS = "pros";
	
	static final String IMG_LOGO_SQUARE = "logo-square";
	static final String IMG_LOGO_WIDERECT = "logo-widerect";
	static final String IMG_LOGO_TALLRECT = "logo-tallrect";
}
