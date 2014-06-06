package com.wicam.numberlineweb.server;

import java.util.ArrayList;

public class Parametermatrix {

	private ArrayList<String> itemList = new ArrayList<String>(); 
	private ArrayList<Integer> idxList = new ArrayList<Integer>(); 
	private ArrayList<Double> CorResp_bm_epsiList = new ArrayList<Double>();  
	private ArrayList<Double> CorResp_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> Cap_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> Cap_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> CapN_bm_epsiList = new ArrayList<Double>();    
	private ArrayList<Double> CapN_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> CapV_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> CapV_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> CapA_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> CapA_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> Gem_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> Gem_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> LenHIE_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> LenHIE_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> LenH_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> LenH_bm_epsi_seList = new ArrayList<Double>();   
	private ArrayList<Double> LenIE_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> LenIE_bm_deltaList = new ArrayList<Double>();   
	private ArrayList<Double> Dev_bm_epsiList = new ArrayList<Double>();   
	private ArrayList<Double> Dev_bm_deltaList = new ArrayList<Double>();  
		
	
	Parametermatrix()
	{
		createLists();
	}
	
	public void addItem (String item, 
			int idx, 
			Double CorResp_bm_epsi, 
			Double CorResp_bm_delta, 
			Double Cap_bm_epsi, 
			Double Cap_bm_delta, 
			Double CapN_bm_epsi, 
			Double CapN_bm_delta, 
			Double CapV_bm_epsi, 
			Double CapV_bm_delta, 
			Double CapA_bm_epsi, 
			Double CapA_bm_delta, 
			Double Gem_bm_epsi, 
			Double Gem_bm_delta, 
			Double LenHIE_bm_epsi, 
			Double LenHIE_bm_delta, 
			Double LenH_bm_epsi, 
			Double LenH_bm_epsi_se, 
			Double LenIE_bm_epsi, 
			Double LenIE_bm_delta, 
			Double Dev_bm_epsi, 
			Double Dev_bm_delta)
	{
		this.itemList.add(item);
		this.idxList.add(idx);
		this.CorResp_bm_epsiList.add(CorResp_bm_epsi);
		this.CorResp_bm_deltaList.add(CorResp_bm_delta);
		this.Cap_bm_epsiList.add(Cap_bm_epsi);
		this.Cap_bm_deltaList.add(Cap_bm_delta);
		this.CapN_bm_epsiList.add(CapN_bm_epsi);
		this.CapN_bm_deltaList.add(CapN_bm_delta);
		this.CapV_bm_epsiList.add(CapV_bm_epsi);
		this.CapV_bm_deltaList.add(CapV_bm_delta);
		this.CapA_bm_epsiList.add(CapA_bm_epsi);
		this.CapA_bm_deltaList.add(CapA_bm_delta);
		this.Gem_bm_epsiList.add(Gem_bm_epsi);
		this.Gem_bm_deltaList.add(Gem_bm_delta);
		this.LenHIE_bm_epsiList.add(LenHIE_bm_epsi);
		this.LenHIE_bm_deltaList.add(LenHIE_bm_delta);
		this.LenH_bm_epsiList.add(LenH_bm_epsi);
		this.LenH_bm_epsi_seList.add(LenH_bm_epsi_se);
		this.LenIE_bm_epsiList.add(LenIE_bm_epsi);
		this.LenIE_bm_deltaList.add(LenIE_bm_delta);
		this.Dev_bm_epsiList.add(Dev_bm_epsi);
		this.Dev_bm_deltaList.add(Dev_bm_delta);
	}
	
	private void createLists()
	{
		addItem("Grimasse",49,1.0931,0.8918,-1.0124,1.6086,-0.9261,1.895,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.9004,1.6274,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("fällen",140,0.5006,1.0721,-2.8927,0.6062,Double.NaN,Double.NaN,-1.4877,1.7936,Double.NaN,Double.NaN,-1.5146,1.5342,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("zerquetschen",133,0.4198,1.299,-2.7797,1.1723,Double.NaN,Double.NaN,-2.1611,2.2482,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schnappt",160,-0.3803,1.2425,-2.0077,2.3375,Double.NaN,Double.NaN,-2.1115,3.4744,Double.NaN,Double.NaN,-0.4113,1.5678,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("fleißiges",179,0.2405,1.1967,-2.7073,1.3518,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.8007,4.3278,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("lackiert",207,0.302,1.4085,-2.8214,0.9718,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.3769,1.3816,-0.6181,0.8912,-0.7062,1.905,Double.NaN,Double.NaN,-0.6565,2.4405,Double.NaN,Double.NaN);
		addItem("Drehung",95,0.5502,1.1611,0.1228,1.0814,0.1045,1.584,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3709,1.5537,-1.2502,0.1367,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Entlassung",48,0.1305,1.5538,-0.0737,1.6773,-0.0744,2.2377,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.1551,2.7002,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Ruhe",98,0.5205,0.808,0.5483,1.1174,0.4514,1.4311,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.0945,3.368,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Rechnen",114,Double.NaN,Double.NaN,2.0266,0.2903,1.0909,0.618,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("passieren",175,-0.4444,1.4927,-2.438,1.4272,Double.NaN,Double.NaN,-2.0169,2.8791,Double.NaN,Double.NaN,-1.096,1.2578,-1.1907,2.0824,Double.NaN,Double.NaN,-1.134,2.478,Double.NaN,Double.NaN);
		addItem("Überschwemmung",22,1.323,0.9383,-0.6386,1.8342,-0.5706,2.332,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.0512,1.4972,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Impfung",120,-0.7857,1.4957,-1.0217,2.3852,-0.937,2.9098,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("knurrend",125,0.6971,1.125,-2.3993,1.4589,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1407,2.1374,-0.5393,2.6337,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.2327,1.2047);
		addItem("heißen",130,-1.5488,1.62,-2.7841,1.4948,Double.NaN,Double.NaN,-2.9747,1.6155,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("dehnen",168,-0.5281,1.4547,-2.9867,0.9274,Double.NaN,Double.NaN,-2.0647,1.9502,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.8629,1.4372,-0.7922,0.1138,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verwöhnt",225,-0.419,1.7146,-2.8957,1.318,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.0282,3.147,Double.NaN,Double.NaN,-0.82,1.9999,-0.8816,0.0926,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Hengst",3,-0.1207,1.1655,-0.9934,2.0511,-0.9024,2.5086,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1159,0.8964);
		addItem("fiel",173,-0.8672,0.6774,-3.1088,1.6553,Double.NaN,Double.NaN,-2.869,2.405,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.9392,0.4959,Double.NaN,Double.NaN,-3.5212,0.5632,Double.NaN,Double.NaN);
		addItem("gequält",237,1.1626,1.4471,-3.4997,0.5817,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.5053,2.1148,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verwirrend",221,0.4955,1.5282,-9.108,0.38,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.8841,1.475,0.0944,1.7999,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.0868,1.8322);
		addItem("Pferderennbahn",33,-0.5818,2.3415,-12.7181,0.349,-4.3673,1.0676,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.9565,2.9911,-1.2451,2.1222,-1.1988,0.2864,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("mittlere",216,0.1281,1.2744,-1.4836,0.4647,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3601,0.5503,-1.3185,1.3637,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schallgeschwindigkeit",41,0.8357,1.0562,-1.6986,1.1955,-1.5733,1.2893,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.6165,1.3685,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Liegestützen",15,-0.2435,1.1224,-1.2117,1.7078,-0.9422,2.638,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.1209,1.2491,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Ausritt",57,-0.0964,1.8155,-0.9305,2.1132,-0.7993,2.7188,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.7872,1.478,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Satz",18,-1.9925,1.2272,-1.6669,1.7086,-1.3544,2.3293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Kummer",25,-0.3452,0.9743,-1.1461,0.6025,-1.2046,0.574,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.9059,1.2563,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("floh",172,-1.9384,0.4283,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.8228,0.7659,-1.6383,0.7119,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Glück",61,-1.8896,1.1263,-1.3051,2.5404,-1.1568,2.9813,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-4.3547,0.8361,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Halle",37,-1.9304,2.0874,-1.9388,2.3796,-1.5553,3.6067,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.294,2.711,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("häufig",235,-0.4802,1.5842,-2.281,1.7843,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1768,2.1629,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Kette",53,-1.4064,3.0012,-1.3732,53.3138,-1.3624,17.2706,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.458,3.3497,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("siegen",176,-0.9234,0.9599,-2.3344,0.5658,Double.NaN,Double.NaN,-1.2083,1.3313,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.3784,1.1511,Double.NaN,Double.NaN,-1.9854,1.6499,Double.NaN,Double.NaN);
		addItem("stumm",191,-0.4402,2.4462,-1.5565,1.4642,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.6816,1.4432,-0.9563,1.994,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Lücke",65,-1.1995,1.6136,-1.758,1.9133,-1.3926,2.8261,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.3936,0.9272,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Hoffnung",73,-0.5168,1.4391,-1.5188,0.9121,-1.3865,1.0036,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.1516,2.4117,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verdoppelt",77,-1.1387,1.4526,-3.0361,1.0734,Double.NaN,Double.NaN,-2.5981,1.4196,Double.NaN,Double.NaN,-1.1336,2.9274,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schick",208,-2.0435,0.8798,-3.1777,1.0162,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1241,1.945,-2.096,1.1817,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Gitarre",85,0.5192,1.2234,-1.3842,52.0663,-1.3727,56.2569,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.2264,2.0781,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Rutschgefahr",92,-0.526,0.6532,-1.2737,0.5412,-0.86,0.815,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.8606,0.7234,-6.9537,10.1815,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("abmessen",144,-0.7161,1.1909,-2.4126,1.4468,Double.NaN,Double.NaN,-2.4346,1.5762,Double.NaN,Double.NaN,-1.4857,2.211,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-9.2675,0.3643);
		addItem("Heimweh",100,0.701,0.5935,0.1983,0.9645,0.1477,1.7365,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.9607,1.7556,-2.2374,0.8201,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("billig",192,-0.5526,2.6068,-2.1608,1.7038,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.8433,2.6462,-1.0027,2.026,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Abschreiben ",112,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,2.3793,-0.9389);
		addItem("klappern",156,-1.5305,0.9614,-2.415,1.0942,Double.NaN,Double.NaN,-2.6032,1.052,Double.NaN,Double.NaN,-1.5013,2.1573,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schlamm",29,-0.459,1.9432,-1.3414,4.0909,-1.1379,6.5617,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.6603,1.7808,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schenkst",128,-1.8912,0.8474,-4.4929,1.0448,Double.NaN,Double.NaN,-6.0219,0.7726,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verschmutzen",134,-1.2621,1.482,-2.0836,2.487,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.3872,1.3997,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("brennen",137,-1.1417,2.248,-1.4698,2.1695,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2998,4.3437,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("niedlich",231,-0.7399,1.74,-2.2698,2.1234,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.0666,1.4476,Double.NaN,Double.NaN,-1.1079,3.5918,Double.NaN,Double.NaN,-1.2604,2.9934,Double.NaN,Double.NaN);
		addItem("treffen",148,-0.9786,1.8821,-1.7964,2.3763,Double.NaN,Double.NaN,-1.8975,2.6867,Double.NaN,Double.NaN,-1.3109,2.6407,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("mühevoll",227,-0.3356,1.1758,-4.3166,0.7957,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3704,134.6028,-1.0451,2.7035,-3.6521,1.0329,-2.8463,1.332,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verwirrst",164,1.1446,2.7461,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,1.0616,3.2025,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("fröhliches",224,-0.791,1.5656,-4.8282,0.584,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7152,1.0892,-1.4607,0.334,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Süppchen",81,-0.1496,1.6907,-1.2225,1.4114,-0.9671,1.987,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.0971,1.5446,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schmutzig",184,-0.8953,1.9825,-2.8566,1.1571,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-7.6876,0.3986,-1.8923,1.2702,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("dennoch",188,-4.271,0.2481,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.3496,0.7666,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Nachsitzen",113,-0.3376,0.6995,-0.9051,0.4686,-0.5551,0.7577,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.212,1.8447,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("stiller",196,-1.3987,1.5072,-5.3326,0.5729,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3612,189.4451,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("unzuverlässig",204,0.4693,2.3129,-1.3978,55.0752,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7191,213.3169,-0.4748,2.6201,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Anziehungskraft",96,0.5339,2.48,-0.2139,1.5324,-0.2377,1.3569,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-5.3072,0.2531,Double.NaN,Double.NaN,-0.1869,2.3966,Double.NaN,Double.NaN);
		addItem("ruppig",212,-0.5926,0.7608,-2.1815,1.5013,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.4863,4.8336,-0.6702,1.3604,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Geburtstag",7,-0.902,1.5705,-1.4464,14.6758,-1.3624,281.357,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Vielfraß",104,1.6631,1.6068,0.7092,0.9964,0.5972,1.2415,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.4253,2.5953,Double.NaN,Double.NaN,-2.9585,1.7062,Double.NaN,Double.NaN);
		addItem("Motorradjacke",12,0.5326,1.5825,-1.7202,39.0219,-2.3521,45.0724,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verstummt",139,-0.2209,1.1294,29.2612,-0.0964,Double.NaN,Double.NaN,-10.95,0.2614,Double.NaN,Double.NaN,-0.7534,0.9293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Ritze",16,-0.4223,1.4969,-0.6895,1.9201,-0.6096,2.3004,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.2396,1.073,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("tief",230,-2.0042,0.9638,-2.3912,0.9114,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.8193,1.5581,Double.NaN,Double.NaN,-2.6242,3.0625,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Kamm",28,-2.5846,0.6766,-3.2134,0.857,-3.5333,0.7916,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.6984,0.9146,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("erfahren",167,-1.5882,1.0424,18.41,-0.2463,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1634,1.2728,-1.9504,0.4748,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Qualle",36,-1.1997,1.0253,-2.0491,21.175,-2.757,17.0895,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3652,1.4752,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("beschäftigt",238,-0.3146,1.1681,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Wette",52,-1.1375,2.6768,-1.5745,2.1612,-1.8772,1.7153,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7317,29.0952,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("plötzlich",187,-2.0304,1.0946,-2.8009,1.2349,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.666,1.0858,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Zaubertrick",60,-1.0012,2.7139,-1.6216,2.7852,-2.1355,1.7381,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.4529,2.364,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schleppt",159,0.2126,2.6941,-2.4135,2.1741,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.0304,2.6533,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Klebstoff",72,-0.6859,2.7011,-2.321,1.4586,-2.5485,1.4071,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.6817,178.0972,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Fotoapparat",76,2.1518,2.6363,-1.4339,18.4013,-2.0735,3.5878,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.9648,2.9042,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schmierig",234,-0.0598,1.4938,-2.1172,1.5174,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1041,1.7832,Double.NaN,Double.NaN,-0.3577,1.5398,Double.NaN,Double.NaN,-0.3448,1.7798,Double.NaN,Double.NaN);
		addItem("Geschirr",84,0.5218,2.4241,-1.1903,4.8778,-1.3393,59.0195,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.2709,1.8335,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("rollt",143,-1.9074,1.1012,-3.6142,0.8914,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1234,1.3553,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Blumenwiese",103,-1.099,1.233,-1.991,3.8694,-2.6053,2.944,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.4297,3.0696,Double.NaN,Double.NaN,-1.5687,2.4505,Double.NaN,Double.NaN);
		addItem("zerknittert",215,0.2803,2.0172,-2.8372,2.0783,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.0643,76.2473,-0.0767,1.8763,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("geschafft",199,0.5021,2.026,-2.7598,1.0338,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.9774,2.0503,0.3421,1.9454,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Bequemlichkeit",119,3.5674,0.6908,3.8639,0.3577,1.9793,0.826,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schaffen",147,-0.8513,1.9822,-2.1384,1.9254,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.8499,3.3144,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("vorsichtig",127,-1.2467,1.0538,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Mannschaftskapitän",32,1.6202,1.492,-1.9279,2.061,-2.1869,1.9694,Double.NaN,Double.NaN,Double.NaN,Double.NaN,1.1141,0.6493,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("wahr ",226,-1.0088,0.9983,-2.4135,2.1741,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.6602,2.0383,Double.NaN,Double.NaN,-0.9283,1.309,-0.9178,0.2421,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Hammer",24,-1.4191,3.9954,-1.7202,39.0219,-2.3521,45.0724,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.5992,3.7837,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("ticken",151,-2.2699,0.832,-5.3073,0.5321,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.4571,1.0266,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Zeh",99,-0.8409,1.6348,-1.2684,2.5076,-1.5638,1.7035,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("scharrt",163,0.4748,1.8175,-3.7585,0.7762,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.2685,1.8782,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Tritt",56,1.5323,0.7642,1.1739,0.6008,0.9323,0.8853,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.4418,2.1218,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("sah",171,-5.0456,0.6745,-2.8372,2.0783,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,3.1519,-1.5646,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Säckchen",64,-0.2192,1.3478,-1.0182,1.5047,-1.1386,1.2956,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2705,1.8139,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("hell",195,-1.7737,1.5851,-2.9238,0.961,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.2229,1.6007,-2.914,1.5527,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Gelegenheit",123,0.2879,1.2519,0.2142,1.6062,0.2591,2.3362,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("klapprig",211,0.351,2.2058,10.2439,-0.3371,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.7791,1.5652,-0.232,1.8456,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schwieriges",116,-11.1129,-0.298,-2.8418,-0.9959,-5.4078,-0.489,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.6695,1.8037,Double.NaN,Double.NaN,-0.6126,2.3317,Double.NaN,Double.NaN);
		addItem("satt",219,-1.3219,1.325,-12.0912,0.2743,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.2382,2.4981,-1.6852,1.2614,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("herrliches",223,0.9577,1.8897,-4.1393,0.6964,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.2715,1.729,0.7773,1.589,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Dieb",107,-1.5039,1.8991,-1.4263,3.8533,-1.8232,2.1809,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-4.954,0.8033,Double.NaN,Double.NaN,-9.0618,0.4198,Double.NaN,Double.NaN);
		addItem("Gestrüpp",80,1.3982,1.7189,-0.4031,1.9836,-0.3812,1.8973,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.9234,2.377,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Pralinen",13,1.4776,0.6888,-1.0818,3.0426,-1.1918,2.4038,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Abschlussball",40,-0.0499,1.2192,-1.504,2.4501,-1.7012,2.1852,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("hofft",149,-1.4001,0.9214,-2.5569,1.9252,Double.NaN,Double.NaN,-6.8925,0.6571,Double.NaN,Double.NaN,-0.8804,1.3141,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("spazierte",132,0.0653,1.6855,-1.7764,1.9229,Double.NaN,Double.NaN,-2.2551,1.6939,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.1448,2.5839,Double.NaN,Double.NaN,-1.1677,3.0125,Double.NaN,Double.NaN);
		addItem("Kälte",117,0.6607,0.2049,-1.6251,0.1732,-1.073,0.2533,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Straßenbahn",109,-1.0102,1.8845,-1.1698,4.6528,-1.1675,4.6619,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.0051,2.1064,-1.5133,0.2892,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Staudamm",30,-0.8923,1.0657,-0.7652,33.1835,-0.6981,143.8807,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.7839,1.2697,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("weiß",181,-3.1607,0.6095,-1.7185,1.4784,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.4574,0.1654,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Wasserquelle",38,-0.4355,0.8386,-1.8285,2.1103,-1.8991,1.9954,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("näher",228,-2.0094,0.6935,6.0398,-0.6669,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3697,180.5629,Double.NaN,Double.NaN,16.6594,-0.1972,-24.4998,166.1797,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schwimmflossen",50,-1.4856,0.9219,-1.8091,1.8722,-1.7442,1.9792,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("friedliebende",232,-1.0022,0.646,3.8052,-0.839,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.5173,1.2166,Double.NaN,Double.NaN,-1.3239,1.4736,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Sprungbrett",58,-0.6353,1.3398,-0.7331,52.5572,-0.8125,21.4128,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.526,1.1686,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("erzählst",169,-1.0697,1.1009,-2.7334,1.0106,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.1382,1.9713,-1.5146,0.4355,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schiff",70,-2.0043,1.925,-1.399,17.6235,-1.4054,15.5031,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7718,2.0239,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Zwerg",9,-1.351,1.7193,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.575,2.5039);
		addItem("Puppentheater",78,0.6485,1.4543,-1.3216,18.2469,-1.3253,19.884,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2594,1.3675,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Nähe",97,1.4487,1.0521,0.9937,2.6028,0.9438,2.51,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,11.4078,-0.3921,60.2265,1196.8439,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("summen",138,-3.5046,0.7113,-5.8161,0.4594,Double.NaN,Double.NaN,-2.4015,1.3649,Double.NaN,Double.NaN,-16.7313,0.2598,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Fernrohr",93,-0.1365,0.6727,-1.296,2.9104,-1.2748,3.0099,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.4516,0.8367,-1.448,0.4109,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("hereinlassen",145,-2.541,0.1057,-4.5567,0.8085,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Vorführung",121,0.3172,2.3861,-0.5232,1.9998,-0.5459,1.9099,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.9411,1.6095,-1.0388,0.3812,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Beschallung",34,0.704,0.8379,-1.3568,1.3382,-1.3144,1.4047,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.2073,2.0955,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("lächelnden",236,0.5129,1.4241,-2.8779,1.0483,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-4.0657,0.7556,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("auffüllen",141,-0.7646,1.7628,-2.5654,1.9166,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2458,1.7851,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("interessant",205,1.5428,1.2953,-4.195,0.6992,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1821,1.6726,1.6793,0.7872,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Radiergummi",26,0.0265,1.7404,-2.1518,1.9558,-2.5542,1.5593,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.3872,1.0073,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("stoppen",157,-1.2976,0.9377,-1.3483,1.7282,Double.NaN,Double.NaN,-1.5343,1.931,Double.NaN,Double.NaN,-2.8261,0.6396,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Mandarinen",14,-0.7031,0.3,-4.3385,0.6751,-4.2308,0.6958,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verzerren",165,0.2415,1.3618,-2.5654,1.9166,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.1367,4.385,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Sieger",101,-0.721,1.5566,-0.7019,2.9868,-0.6717,3.3659,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3895,2.4225,Double.NaN,Double.NaN,-1.3473,3.3129,Double.NaN,Double.NaN);
		addItem("Abfall",42,-0.0843,1.354,-0.4931,1.9171,-0.422,2.6236,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2482,1.9621,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.9699,1.5866);
		addItem("verspritzen",185,-0.6763,1.3756,-10.9644,0.293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.512,1.7995,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schlimmer",189,-1.7966,1.211,-2.3333,1.749,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3719,147.1692,-2.6312,1.067,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("dick",209,-1.7828,1.1314,-1.6667,1.2079,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.6029,1.4747,-2.5107,1.6493,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schuppig",213,-0.5733,0.7338,-2.4525,1.0476,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.4267,0.7684,-1.2922,1.3248,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Höhle",90,-0.3899,1.4938,-3.4926,1.3093,-3.0831,1.5194,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.5232,1.1684,-0.4779,0.2043,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("klettern",161,-1.4933,1.1418,-1.4466,1.8811,Double.NaN,Double.NaN,-2.1498,1.3229,Double.NaN,Double.NaN,-1.4259,2.2198,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schafft",201,-1.0594,0.6686,-9.545,0.3364,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.59,1.3187,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Glassplitter",54,0.3506,1.2257,-0.8511,2.2385,-0.9129,1.9932,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.3892,1.4668,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Rücksicht",62,0.9938,1.0105,0.9026,0.9131,0.7632,1.1218,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.0189,1.0487,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Strumpfhose",5,-0.604,1.4315,-1.4528,1.699,-1.4518,1.7072,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Abschleppwagen",82,0.7989,1.2839,-3.6542,1.0247,-3.9247,0.9507,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.5569,1.1237,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.571,1.6644);
		addItem("freiwillig",193,-1.1059,0.8267,5.1923,-0.6293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.889,2.1598,-0.7446,2.4195,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Kartoffelpuffer",74,-0.6845,0.8816,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schmollt",142,0.1915,1.4653,-0.0496,64.9496,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.1052,1.443,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Geduld",8,0.2431,1.1202,-2.1647,0.9199,-1.5412,1.452,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.6204,1.8725);
		addItem("trifft",150,-0.2363,1.5929,-2.27,45.4602,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.3791,1.72,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Vorratskammer",23,0.9949,0.9096,-0.3618,2.9316,-0.8432,2.1695,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("sehnst",170,-0.8382,1.0316,-1.8651,1.7997,Double.NaN,Double.NaN,-2.0725,2.9852,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.4079,1.0277,-2.2162,1.0267,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Fernsehprogramm",31,1.1149,1.2719,-3.0617,1.2648,-1.9901,2.3082,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.5105,1.6249,-1.224,2.5151,-1.4842,0.2862,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("erwidert",178,-4.3252,-0.1395,-2.27,45.4602,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Rolltreppe",39,-0.8425,1.8926,-0.0411,78.3931,-0.6843,314.099,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("gierig",233,-1.7468,0.9136,-2.27,45.4602,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7201,41.3037,Double.NaN,Double.NaN,-1.6299,2.1278,Double.NaN,Double.NaN,-1.4841,2.6563,Double.NaN,Double.NaN);
		addItem("Schlüssel",51,-1.8721,1.3437,-2.8629,1.1003,-1.9799,1.7875,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.3284,2.7883,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("entsetzliche",186,1.3192,0.728,-3.2993,0.819,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.0417,1.0961,-2.8926,0.4525,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Doppelbett",59,-0.9376,2.1001,-1.1932,1.5662,-1.3454,1.6976,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Tischdecke",67,-1.7016,1.1307,-1.8676,1.483,-1.5809,2.0321,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.1245,1.1953,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("krumm",190,0.7211,1.2975,-1.1258,2.0633,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.0307,1.8408,0.0302,1.827,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Klippe ",75,-0.8237,2.3258,-3.1261,73.1767,-2.3312,186.5795,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.166,2.5779,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Galopp ",79,4.6585,0.3299,-0.5057,0.9962,-0.6955,1.0685,Double.NaN,Double.NaN,Double.NaN,Double.NaN,1.014,0.5949,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Irrtum",87,1.3634,1.5296,0.3306,1.8073,0.0744,1.8104,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.7159,1.6205,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("pfiffig",198,0.086,0.8648,-0.0237,60.8007,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.0699,1.7591,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Eselsohr",94,-2.1017,0.8611,-2.5798,1.0609,-1.9398,1.5595,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.7459,0.1958,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schief",229,-1.5737,1.408,-2.27,45.4602,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7201,41.3037,Double.NaN,Double.NaN,-1.781,1.7169,Double.NaN,Double.NaN,-1.7117,1.8027,Double.NaN,Double.NaN);
		addItem("Außenspiegel",110,1.9757,1.3266,-0.007,91.7447,-0.0205,167.7678,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.8247,2.1899,Double.NaN,Double.NaN,-1.8314,2.0217,Double.NaN,Double.NaN);
		addItem("Üben",115,Double.NaN,Double.NaN,-12.4615,-0.1409,-40.6537,-0.0446,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Geschwindigkeit",118,0.3145,0.8983,-0.0051,3.6813,-0.5451,1.7453,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Heiterkeit",122,0.3698,0.4198,0.5725,1.228,0.3197,0.9138,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("weiß",129,-0.0454,0.6669,-3.0846,65.32,Double.NaN,Double.NaN,-2.6811,1.3225,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Gehirnerschütterung",55,0.3515,1.6202,-0.0221,112.7327,-1.1584,3.2143,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.2975,3.0813,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("vergisst",146,0.1886,1.3638,-3.1261,73.1767,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.2118,1.8048,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("draußen",182,-0.0954,1.8904,-2.6888,1.0153,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("kippt",158,-0.3162,2.8272,-2.27,45.4602,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.5425,2.0041,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Fenstergriff",71,-0.2731,2.5219,-0.0221,112.7457,-0.6807,428.3244,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.29,3.256,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("versperren",166,-0.2318,2.0483,-0.061,40.7849,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.3771,3.2471,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Hausaufgabenkontrolle",35,-0.9848,1.9073,-2.27,45.4602,-0.8751,244.7293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.7035,69.4136,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("hässlich",202,0.6618,0.9923,-2.5487,1.0744,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7811,3.0851,-0.5088,1.24,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Frühling",91,-0.9675,1.9732,-1.0463,2.564,-1.2071,3.005,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3555,140.1117,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("knapp",210,-1.4291,1.4664,-3.1261,73.1767,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.3858,42.6536,-1.7079,1.1991,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schokoriegel",102,-0.6269,0.5745,-0.0311,80.0228,-0.6808,408.1731,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.9612,2.2016,Double.NaN,Double.NaN,-0.9269,2.5457,Double.NaN,Double.NaN);
		addItem("zerschmettert",162,-0.8445,0.916,-0.0178,96.2154,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.7779,2.2993,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("lockig",206,-0.2432,1.9413,-0.1888,2.5102,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.8941,3.3499,-0.9347,1.6909,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("spiegelglatt",218,0.6205,1.5794,-0.2592,1.1293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.7149,1.7087,-0.6018,2.315,-1.7756,2.375,Double.NaN,Double.NaN,-1.6946,2.5063,Double.NaN,Double.NaN);
		addItem("sperrig",222,-0.3461,1.3436,-2.27,45.4602,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7201,41.3037,-0.5391,2.0234,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Pflanzen",4,-1.9483,1.0341,-0.0116,147.4795,-0.9797,2.913,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schusswaffe",47,-0.6891,1.7068,-0.0238,104.5952,-0.6814,336.3574,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Handtuch",11,-0.7824,2.1753,-2.27,45.4602,-0.8751,244.7293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Nummernschild",27,-0.697,1.2856,-2.27,45.4602,-0.8751,244.7293,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2671,1.3508,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("gezwickt",153,-0.9496,0.9964,-1.7184,2.3957,Double.NaN,Double.NaN,-2.0778,2.9508,Double.NaN,Double.NaN,-1.6152,0.828,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Fingerring",2,-1.9496,0.7669,-1.5609,2.3765,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("grässlich",203,0.6756,1.6665,-1.6717,3.3481,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7165,233.0744,-0.4669,1.2928,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Aprilscherz",6,0.0565,1.0955,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("bitter",214,-1.5645,1.4573,-1.5833,2.3134,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.5511,4.4054,-1.7415,3.0004,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Schimmelkäse",21,-0.2999,1.58,-1.2592,1.7582,-0.987,2.3411,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.4389,1.0655,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Rehkitz",20,0.6105,0.9823,-2.0825,2.106,-1.3263,17.5062,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.4035,0.5101,-1.8411,1.2236,-2.7142,1.0544,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schneller",197,-1.6917,2.5093,-1.3143,4.6725,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.2365,2.0258,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Bilderrahmen",88,0.3449,1.8847,-5.3656,0.5747,-2.3042,1.4182,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,0.1887,1.8327,0.1296,0.2069,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Ladenschluss",46,-1.6959,0.6077,-1.7812,1.3245,-1.1641,2.28,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.9324,0.8571,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("beißen",131,-0.3783,1.153,-1.6066,2.7409,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("vierblättriges",217,0.5312,1.5564,-4.47,0.2459,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.0057,0.6111,-1.0083,1.3929,-2.8706,1.3229,Double.NaN,Double.NaN,-2.6774,1.4023,Double.NaN,Double.NaN);
		addItem("Zuckerwatte",69,-0.9344,2.0778,-1.8642,1.7606,-1.3206,2.8945,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Spaßvogel",105,-0.7893,1.9056,-6.361,0.3656,-2.692,0.8654,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("stützt",136,-0.6658,1.6758,-1.2067,3.4595,Double.NaN,Double.NaN,-1.9905,2.0182,Double.NaN,Double.NaN,-1.4599,1.5597,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Trockenfutter",68,-0.5413,1.4668,-1.7182,1.1524,-1.1338,1.8637,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Druckfehler",89,0.11,1.7695,-1.3082,1.4949,-0.9353,2.2558,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.8727,1.7231,-0.9073,3.2856,-1.0921,0.1709,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Pfarrer",86,-0.1565,1.424,-2.093,2.0885,-1.5221,3.3992,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.4483,1.4022,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Zebrastreifen",10,-1.5674,1.3141,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Herr",83,-5.7519,0.7772,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.9083,1.2996,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Unfall",43,-1.379,1.8185,-1.7572,1.9318,-0.8224,18.4532,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.8186,2.1344,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Maß",108,0.1585,1.7626,-1.1238,1.3712,-0.6187,3.3583,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verblüfft",200,1.1978,1.7554,-2.41,1.2395,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.7167,3.02,1.1105,1.8766,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Seitenhieb",106,0.453,0.5891,-2.8521,0.4977,-1.5416,0.9056,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-3.1503,0.2165,Double.NaN,Double.NaN,-2.671,0.2446,-2.543,0.8304);
		addItem("Riss",44,-0.5929,0.9508,-1.5468,0.8659,-0.8644,1.6045,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.209,0.9305,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Päckchen",63,-0.5124,1.3463,-2.1588,2.7227,-2.601,1.7837,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.6722,0.5256,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schnitzen",135,-0.3033,2.0578,-0.7623,1.2103,Double.NaN,Double.NaN,-0.9026,1.5805,Double.NaN,Double.NaN,-1.5926,1.8758,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verdreckt",126,0.1151,1.3618,-1.5878,0.842,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2661,1.4256,-2.6593,0.6553,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("quergestreift",183,0.3652,1.6213,-0.5708,1.1765,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.6516,1.7169,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("blicken",152,-0.9959,1.612,-3.4906,0.7335,Double.NaN,Double.NaN,-3.4599,0.8331,Double.NaN,Double.NaN,-2.6684,1.0673,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("verliert",174,-0.561,1.6371,-1.7077,40.7091,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.108,1.4125,Double.NaN,Double.NaN,-0.7853,2.095,Double.NaN,Double.NaN);
		addItem("Biss",45,-0.2456,1.1872,-0.4819,1.2308,-0.2371,2.3412,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.5599,1.2488,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("zuklappen",155,-0.4601,1.9435,-1.6746,3.3347,Double.NaN,Double.NaN,-2.4533,2.503,Double.NaN,Double.NaN,-0.9229,1.9369,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("schließlich",180,-0.2929,1.6388,-0.9911,70.2687,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.9089,2.5972,Double.NaN,Double.NaN,-0.7432,2.9153,Double.NaN,Double.NaN);
		addItem("fällt",177,-0.3183,1.878,-2.0116,2.2385,Double.NaN,Double.NaN,-3.0041,1.5739,Double.NaN,Double.NaN,-0.6003,2.4247,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Vogelstrauß",111,-0.8908,0.6847,-3.4673,1.3044,-3.6549,1.1854,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("geschickt",154,-0.7579,0.9317,-1.3499,3.244,Double.NaN,Double.NaN,-1.9958,2.4771,Double.NaN,Double.NaN,-1.5609,0.6662,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Kühlschrank",1,-0.7962,2.1849,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.3137,2.8792,-1.3636,0.1994,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("voll",194,-1.1788,2.2071,-1.3598,28.0731,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-2.2464,2.683,-2.7125,1.8543,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Stützrädern",19,0.0247,3.4571,-1.4415,1.2,-0.8877,2.1689,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.2766,2.4779,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("starr",220,-0.2044,2.1655,-1.0875,3.0791,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.5014,2.3614,-0.4482,2.6929,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Mücke",66,-1.8481,1.3783,-2.7952,1.3695,-1.3527,83.1131,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-1.9047,1.9109,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
		addItem("Wolkenkratzer",17,-0.1312,1.7748,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,-0.9156,1.6924,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN);
	}
	
	ArrayList<ArrayList<Double>> getEpsiThree ()
	{
		ArrayList<ArrayList<Double>> epsiThree = new ArrayList<ArrayList<Double>>();
		epsiThree.add(Cap_bm_epsiList);
		epsiThree.add(Gem_bm_epsiList);
		epsiThree.add(LenHIE_bm_epsiList);
		return epsiThree;
	}
	
	ArrayList<ArrayList<Double>> getDeltaThree ()
	{
		ArrayList<ArrayList<Double>> deltaThree = new ArrayList<ArrayList<Double>>();
		deltaThree.add(Cap_bm_deltaList);
		deltaThree.add(Gem_bm_deltaList);
		deltaThree.add(LenHIE_bm_deltaList);
		return deltaThree;
	}
}
