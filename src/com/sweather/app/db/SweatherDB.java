package com.sweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sweather.app.model.City;
import com.sweather.app.model.County;
import com.sweather.app.model.Province;

public class SweatherDB {

	private static final String DB_Name="S_weather";
	
	public static final int VERSION=1;
	
	private static SweatherDB sweatherDB;
	
	private SQLiteDatabase db;
	
	private SweatherDB(Context context){
		
		SweatherOpenHelper dbHelper=new SweatherOpenHelper(context,
				DB_Name,null,VERSION);
		db=dbHelper.getWritableDatabase();
		
	}
	
	
	public synchronized static SweatherDB getInstance(Context context){
		if(sweatherDB==null){
			sweatherDB=new SweatherDB(context);
		}
		
		return sweatherDB;
	}
	
	public void saveProvince(Province province){
		if(province!=null){
			//ContentValues values=new ContentValues();
			//values.put("province_name", province.getProvinceName());
			//values.put("province_code", province.getProvinceCode());
			db.execSQL("insert into Provice(province_name,province_code) values(?,?)",
					new String[]{province.getProvinceName(),province.getProvinceCode()}); 
		}
	}
	
	public List<Province> loadProvince(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.rawQuery("select * from Provice", null);
		if(cursor.moveToFirst()){
		do{
			Province province=new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			list.add(province);
		  }while(cursor.moveToNext());
		}
		
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	public void saveCity(City city){
		if(city!=null){
		
			db.execSQL("insert into City(city_name,city_code,province_id) values(?,?,?)",
					new String[]{city.getCityName(),city.getCityCode(),city.getProvinceId()+""}); 
		}
	}
	
	public List<City> loadCity(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.rawQuery("select * from City where province_id=?",new String[]{String.valueOf(provinceId)});
		if(cursor.moveToFirst()){
		do{
			City city=new City();
			city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setProvinceId(provinceId);
			list.add(city);
		  }while(cursor.moveToNext());
		}
		
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	
	public void saveCounty(County county){
		if(county!=null){
		
			db.execSQL("insert into County(county_name,county_code,city_id) values(?,?,?)",
					new String[]{county.getCountyName(),county.getCountyCode(),county.getCityId()+""}); 
		}
	}
	
	public List<County> loadCounty(int cityid){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.rawQuery("select * from County where city_id=? ", new String[]{String.valueOf(cityid)});
		if(cursor.moveToFirst()){
		do{
			County county=new County();
			county.setId(cursor.getInt(cursor.getColumnIndex("id")));
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCityId(cityid);
			list.add(county);
		  }while(cursor.moveToNext());
		}
		
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	
}
