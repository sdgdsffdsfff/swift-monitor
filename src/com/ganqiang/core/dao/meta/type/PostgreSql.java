package com.ganqiang.core.dao.meta.type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ganqiang.core.dao.DAOException;
import com.ganqiang.core.dao.meta.Column;
import com.ganqiang.core.dao.meta.Constraint;
import com.ganqiang.core.dao.meta.DataSourceInfo;
import com.ganqiang.core.dao.meta.JDBCUtil;
import com.ganqiang.core.util.common.StringUtil;

public class PostgreSql extends DBAdaptor
{
  private DataSourceInfo dataSourceInfo;
  
  public PostgreSql(DataSourceInfo ds){
    this.dataSourceInfo = ds;
  }
  
  public DataSourceInfo getDataSourceInfo()
  {
    return dataSourceInfo;
  }

  public void setDataSourceInfo(DataSourceInfo dataSourceInfo)
  {
    this.dataSourceInfo = dataSourceInfo;
  }
  
  
  String getSearchTableSql(DataSourceInfo ds)
  {
    String sql = null;
    switch (ds.getDbVersion()) {
      case POSTGRESQL_7:
        sql = "select table_name from information_schema.tables where table_schema = 'public' and table_catalog='"+ds.getInstanceName()+"' "+
              "and table_type = 'BASE TABLE' and is_insertable_into = 'YES' order by table_name ";
      case POSTGRESQL_8:
        sql = "select table_name from information_schema.tables where table_schema = 'public' and table_catalog='"+ds.getInstanceName()+"' "+
              "and table_type = 'BASE TABLE' and is_insertable_into = 'YES' order by table_name ";
      case POSTGRESQL_9:
        sql = "select table_name from information_schema.tables where table_schema = 'public' and table_catalog='"+ds.getInstanceName()+"' "+
              "and table_type = 'BASE TABLE' and is_insertable_into = 'YES' order by table_name ";
    } 
    return sql; 
  }

  
  String getSearchColumnSql(DataSourceInfo ds)
  {
    String sql = null;
    switch (ds.getDbVersion()) {
      case POSTGRESQL_7:
        sql = "select * from (select table_catalog,table_schema,table_name,column_name,column_default, "+
              "is_nullable,data_type,character_maximum_length,numeric_precision,numeric_scale,datetime_precision "+
              "from information_schema.columns where table_catalog='cwconsole2' and table_schema='public' order by table_name) as e "+
              "left join (select k.table_catalog,k.table_schema,k.table_name,k.column_name,t.constraint_type "+
              "from information_schema.key_column_usage as k,information_schema.table_constraints as t "+
              "where k.table_catalog='"+ds.getInstanceName()+"' and k.table_schema='public' "+
              "and k.table_catalog=t.table_catalog and k.table_schema=t.table_schema and t.constraint_name=k.constraint_name "+
              "and (t.constraint_type='PRIMARY KEY' or t.constraint_type='FOREIGN KEY') ) as f "+
              "on e.table_catalog=f.table_catalog and e.table_schema=f.table_schema  "+
              "and e.table_name=f.table_name and e.column_name=f.column_name "+
              "left join (SELECT c.relname as table_name,a.attname as column_name,col_description(a.attrelid,a.attnum) as comment "+
              "FROM pg_class as c,pg_attribute as a where a.attrelid = c.oid and a.attnum>0 ) as g "+
              "on g.table_name=e.table_name and e.column_name=g.column_name";
      case POSTGRESQL_8:
        sql = "select * from (select table_catalog,table_schema,table_name,column_name,column_default, "+
              "is_nullable,data_type,character_maximum_length,numeric_precision,numeric_scale,datetime_precision "+
              "from information_schema.columns where table_catalog='cwconsole2' and table_schema='public' order by table_name) as e "+
              "left join (select k.table_catalog,k.table_schema,k.table_name,k.column_name,t.constraint_type "+
              "from information_schema.key_column_usage as k,information_schema.table_constraints as t "+
              "where k.table_catalog='"+ds.getInstanceName()+"' and k.table_schema='public' "+
              "and k.table_catalog=t.table_catalog and k.table_schema=t.table_schema and t.constraint_name=k.constraint_name "+
              "and (t.constraint_type='PRIMARY KEY' or t.constraint_type='FOREIGN KEY') ) as f "+
              "on e.table_catalog=f.table_catalog and e.table_schema=f.table_schema  "+
              "and e.table_name=f.table_name and e.column_name=f.column_name "+
              "left join (SELECT c.relname as table_name,a.attname as column_name,col_description(a.attrelid,a.attnum) as comment "+
              "FROM pg_class as c,pg_attribute as a where a.attrelid = c.oid and a.attnum>0 ) as g "+
              "on g.table_name=e.table_name and e.column_name=g.column_name";
      case POSTGRESQL_9:
        sql = "select * from (select table_catalog,table_schema,table_name,column_name,column_default, "+
              "is_nullable,data_type,character_maximum_length,numeric_precision,numeric_scale,datetime_precision "+
              "from information_schema.columns where table_catalog='cwconsole2' and table_schema='public' order by table_name) as e "+
              "left join (select k.table_catalog,k.table_schema,k.table_name,k.column_name,t.constraint_type "+
              "from information_schema.key_column_usage as k,information_schema.table_constraints as t "+
              "where k.table_catalog='"+ds.getInstanceName()+"' and k.table_schema='public' "+
              "and k.table_catalog=t.table_catalog and k.table_schema=t.table_schema and t.constraint_name=k.constraint_name "+
              "and (t.constraint_type='PRIMARY KEY' or t.constraint_type='FOREIGN KEY') ) as f "+
              "on e.table_catalog=f.table_catalog and e.table_schema=f.table_schema  "+
              "and e.table_name=f.table_name and e.column_name=f.column_name "+
              "left join (SELECT c.relname as table_name,a.attname as column_name,col_description(a.attrelid,a.attnum) as comment "+
              "FROM pg_class as c,pg_attribute as a where a.attrelid = c.oid and a.attnum>0 ) as g "+
              "on g.table_name=e.table_name and e.column_name=g.column_name";
    } 
    return sql;  
  }

  
  String getSearchConstraintSql(DataSourceInfo ds)
  {
    String sql = null;
    switch (ds.getDbVersion()) {
      case POSTGRESQL_7:
        sql = "select t.constraint_name,k.table_name as table_name,k.column_name as column_name,c.table_name as referenced_table_name,c.column_name as referenced_column_name "+
              "from information_schema.table_constraints as t,information_schema.key_column_usage as k,information_schema.constraint_column_usage as c "+
              "where k.table_catalog='"+ds.getInstanceName()+"' and k.table_schema='public' "+
              "and k.table_catalog=t.table_catalog and c.table_catalog=t.table_catalog and k.table_schema=t.table_schema and c.table_schema=t.table_schema "+
              "and t.table_name=k.table_name and t.constraint_name=k.constraint_name and c.constraint_name=k.constraint_name "+
              "and t.constraint_type='FOREIGN KEY'";
      case POSTGRESQL_8:
        sql = "select t.constraint_name,k.table_name as table_name,k.column_name as column_name,c.table_name as referenced_table_name,c.column_name as referenced_column_name "+
              "from information_schema.table_constraints as t,information_schema.key_column_usage as k,information_schema.constraint_column_usage as c "+
              "where k.table_catalog='"+ds.getInstanceName()+"' and k.table_schema='public' "+
              "and k.table_catalog=t.table_catalog and c.table_catalog=t.table_catalog and k.table_schema=t.table_schema and c.table_schema=t.table_schema "+
              "and t.table_name=k.table_name and t.constraint_name=k.constraint_name and c.constraint_name=k.constraint_name "+
              "and t.constraint_type='FOREIGN KEY'";
      case POSTGRESQL_9:
        sql = "select t.constraint_name,k.table_name as table_name,k.column_name as column_name,c.table_name as referenced_table_name,c.column_name as referenced_column_name "+
              "from information_schema.table_constraints as t,information_schema.key_column_usage as k,information_schema.constraint_column_usage as c "+
              "where k.table_catalog='"+ds.getInstanceName()+"' and k.table_schema='public' "+
              "and k.table_catalog=t.table_catalog and c.table_catalog=t.table_catalog and k.table_schema=t.table_schema and c.table_schema=t.table_schema "+
              "and t.table_name=k.table_name and t.constraint_name=k.constraint_name and c.constraint_name=k.constraint_name "+
              "and t.constraint_type='FOREIGN KEY'";
    } 
    return sql;  
  }

  
  public List<String> getAllTableName()  throws DAOException
  {
    String tableNameSql = getSearchTableSql(dataSourceInfo);
    return super.getAllTableName(dataSourceInfo,tableNameSql);
  }

  public List<Constraint> getAllConstraint()
  {
    String constraintSql = getSearchConstraintSql(dataSourceInfo);
    return super.getAllConstraint(dataSourceInfo, constraintSql);
  }
  
  public List<Column> getAllColumn()
  {
    String columnSql = getSearchColumnSql(dataSourceInfo);
    List<Column> columnList = new ArrayList<Column>();
    Connection con = JDBCUtil.newConnection(dataSourceInfo);
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = con.prepareStatement(columnSql);
      rs=pstmt.executeQuery();
      while (rs.next()){   
        Column column = new Column();
        column.setTableName(rs.getString(3));
        column.setName(rs.getString(4));
        if(!StringUtil.isNullOrBlank(rs.getString(5))){//注意：postgresql中的默认值的格式：'r'::bpchar
          column.setDefaultValue(rs.getString(5).replace("'", "''"));//目前不做转换
        }
        column.setNecessary("NO".equalsIgnoreCase(rs.getString(6))?"1":"0");
        column.setType(rs.getString(7));
        if(!StringUtil.isNullOrBlank(rs.getString(8))){
          column.setLength(rs.getInt(8));//时间类型默认长度
        }else if(!StringUtil.isNullOrBlank(rs.getString(9))){
          column.setLength(rs.getInt(9));//数字类型默认长度
        }else if(!StringUtil.isNullOrBlank(rs.getString(11))){
          column.setLength(rs.getInt(11));//时间类型默认长度
        }
        if(!StringUtil.isNullOrBlank(rs.getString(10))){
          column.setPrecision(rs.getInt(10));
        }
        if(!StringUtil.isNullOrBlank(rs.getString(16))){
          if("PRIMARY KEY".equalsIgnoreCase(rs.getString(16))){
            column.setIsPrimaryKey("1");
          }else if("FOREIGN KEY".equalsIgnoreCase(rs.getString(16))){
            column.setIsForeignKey("1");
          }
        }
        column.setComment(rs.getString(19));
        columnList.add(column);
      }
    } catch (SQLException e) {
      throw new DAOException("Cannot to excute PostgreSql find getAllColumn by columnSql : "+columnSql, e);
    } finally{
      JDBCUtil.free(rs, pstmt, con);
    }
    return columnList;
  }



}
