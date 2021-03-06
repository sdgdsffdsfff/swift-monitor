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

public class Oracle extends DBAdaptor{

  private DataSourceInfo dataSourceInfo;
  
  public Oracle(DataSourceInfo ds){
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
      case ORACLE_8I:
        sql = "select table_name from USER_TABLES where table_name not like '%OGGQT$%' and table_name not like 'SYS_IOT_OVER_%' and table_name not like 'BIN$%' order by table_name";
      case ORACLE_9I:
        sql = "select table_name from USER_TABLES where table_name not like '%OGGQT$%' and table_name not like 'SYS_IOT_OVER_%' and table_name not like 'BIN$%' order by table_name";
      case ORACLE_10G:
        sql = "select table_name from USER_TABLES where table_name not like '%OGGQT$%' and table_name not like 'SYS_IOT_OVER_%' and table_name not like 'BIN$%' order by table_name";
      case ORACLE_11G:
        sql = "select table_name from USER_TABLES where table_name not like '%OGGQT$%' and table_name not like 'SYS_IOT_OVER_%' and table_name not like 'BIN$%' order by table_name";
    } 
    return sql;    
  }
  
  String getSearchColumnSql(DataSourceInfo ds)
  {
    String sql = null;
    switch (ds.getDbVersion()) {
      case ORACLE_8I:
        sql = "select * from  (select u1.table_name,u1.column_name,u1.data_type,u1.data_length,u1.data_scale,u1.nullable,u1.data_default,u2.comments "+
              "from user_tab_columns u1,user_col_comments u2 where u1.table_name=u2.table_name and u1.column_name=u2.column_name "+
              "and u1.table_name not like 'BIN$%' and u1.table_name not like '%OGGQT$%' and u1.table_name not like 'SYS_IOT_OVER_%') u5 " +
              "left join (select u3.table_name,u4.column_name,u3.constraint_type " +
              "from user_constraints u3,user_cons_columns u4 where u3.constraint_name=u4.constraint_name " +
              "and (u3.constraint_type='P' or u3.constraint_type='R') and u3.table_name not like 'BIN$%' " +
              "and u3.table_name not like '%OGGQT$%' and u3.table_name not like 'SYS_IOT_OVER_%') u6 " +
              "on u5.table_name=u6.table_name and u5.column_name=u6.column_name order by u5.table_name";
      case ORACLE_9I:
        sql = "select * from  (select u1.table_name,u1.column_name,u1.data_type,u1.data_length,u1.data_scale,u1.nullable,u1.data_default,u2.comments "+
              "from user_tab_columns u1,user_col_comments u2 where u1.table_name=u2.table_name and u1.column_name=u2.column_name "+
              "and u1.table_name not like 'BIN$%' and u1.table_name not like '%OGGQT$%' and u1.table_name not like 'SYS_IOT_OVER_%') u5 " +
              "left join (select u3.table_name,u4.column_name,u3.constraint_type " +
              "from user_constraints u3,user_cons_columns u4 where u3.constraint_name=u4.constraint_name " +
              "and (u3.constraint_type='P' or u3.constraint_type='R') and u3.table_name not like 'BIN$%' " +
              "and u3.table_name not like '%OGGQT$%' and u3.table_name not like 'SYS_IOT_OVER_%') u6 " +
              "on u5.table_name=u6.table_name and u5.column_name=u6.column_name order by u5.table_name";
      case ORACLE_10G:
        sql = "select * from  (select u1.table_name,u1.column_name,u1.data_type,u1.data_length,u1.data_scale,u1.nullable,u1.data_default,u2.comments "+
              "from user_tab_columns u1,user_col_comments u2 where u1.table_name=u2.table_name and u1.column_name=u2.column_name "+
              "and u1.table_name not like 'BIN$%' and u1.table_name not like '%OGGQT$%' and u1.table_name not like 'SYS_IOT_OVER_%') u5 " +
              "left join (select u3.table_name,u4.column_name,u3.constraint_type " +
              "from user_constraints u3,user_cons_columns u4 where u3.constraint_name=u4.constraint_name " +
              "and (u3.constraint_type='P' or u3.constraint_type='R') and u3.table_name not like 'BIN$%' " +
              "and u3.table_name not like '%OGGQT$%' and u3.table_name not like 'SYS_IOT_OVER_%') u6 " +
              "on u5.table_name=u6.table_name and u5.column_name=u6.column_name order by u5.table_name";
      case ORACLE_11G:
        sql = "select * from  (select u1.table_name,u1.column_name,u1.data_type,u1.data_length,u1.data_scale,u1.nullable,u1.data_default,u2.comments "+
              "from user_tab_columns u1,user_col_comments u2 where u1.table_name=u2.table_name and u1.column_name=u2.column_name "+
              "and u1.table_name not like 'BIN$%' and u1.table_name not like '%OGGQT$%' and u1.table_name not like 'SYS_IOT_OVER_%') u5 " +
              "left join (select u3.table_name,u4.column_name,u3.constraint_type " +
              "from user_constraints u3,user_cons_columns u4 where u3.constraint_name=u4.constraint_name " +
              "and (u3.constraint_type='P' or u3.constraint_type='R') and u3.table_name not like 'BIN$%' " +
              "and u3.table_name not like '%OGGQT$%' and u3.table_name not like 'SYS_IOT_OVER_%') u6 " +
              "on u5.table_name=u6.table_name and u5.column_name=u6.column_name order by u5.table_name";
    } 
    return sql;  
  }


  
  String getSearchConstraintSql(DataSourceInfo ds)
  {
    String sql = null;
    switch (ds.getDbVersion()) {
      case ORACLE_8I:
        sql = "select u1.CONSTRAINT_NAME, u1.TABLE_NAME,u3.column_name,u2.TABLE_NAME as referenced_table_name,u4.column_name as referenced_column_name "+
              "from user_constraints u1, user_constraints u2,user_cons_columns u3,user_cons_columns u4 where u1.constraint_name=u3.constraint_name and "+
              "u2.constraint_name=u4.constraint_name and u1.constraint_type='R' and  u1.R_CONSTRAINT_NAME = u2.CONSTRAINT_NAME";
      case ORACLE_9I:
        sql = "select u1.CONSTRAINT_NAME, u1.TABLE_NAME,u3.column_name,u2.TABLE_NAME as referenced_table_name,u4.column_name as referenced_column_name "+
              "from user_constraints u1, user_constraints u2,user_cons_columns u3,user_cons_columns u4 where u1.constraint_name=u3.constraint_name and "+
              "u2.constraint_name=u4.constraint_name and u1.constraint_type='R' and  u1.R_CONSTRAINT_NAME = u2.CONSTRAINT_NAME";
      case ORACLE_10G:
        sql = "select u1.CONSTRAINT_NAME, u1.TABLE_NAME,u3.column_name,u2.TABLE_NAME as referenced_table_name,u4.column_name as referenced_column_name "+
              "from user_constraints u1, user_constraints u2,user_cons_columns u3,user_cons_columns u4 where u1.constraint_name=u3.constraint_name and "+
              "u2.constraint_name=u4.constraint_name and u1.constraint_type='R' and  u1.R_CONSTRAINT_NAME = u2.CONSTRAINT_NAME";
      case ORACLE_11G:
        sql = "select u1.CONSTRAINT_NAME, u1.TABLE_NAME,u3.column_name,u2.TABLE_NAME as referenced_table_name,u4.column_name as referenced_column_name "+
              "from user_constraints u1, user_constraints u2,user_cons_columns u3,user_cons_columns u4 where u1.constraint_name=u3.constraint_name and "+
              "u2.constraint_name=u4.constraint_name and u1.constraint_type='R' and  u1.R_CONSTRAINT_NAME = u2.CONSTRAINT_NAME";
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
        column.setTableName(rs.getString(1));
        column.setName(rs.getString(2));
        column.setType(rs.getString(3));
        if(!StringUtil.isNullOrBlank(rs.getString(4))){
          column.setLength(rs.getInt(4));
        }
        if(!StringUtil.isNullOrBlank(rs.getString(5))){
          column.setPrecision(rs.getInt(5));
        }
        column.setNecessary("N".equalsIgnoreCase(rs.getString(6))?"1":"0");
        Object obj = rs.getObject(7);
        if(null != obj){
          String defaultValue = obj.toString().replaceAll("'", "");
          if(200 >= defaultValue.length()){
            column.setDefaultValue(defaultValue);
          }else{
            column.setDefaultValue(defaultValue.substring(0,200));
          }
        }
        column.setComment(rs.getString(8));
        if(!StringUtil.isNullOrBlank(rs.getString(11))){
          if("P".equalsIgnoreCase(rs.getString(11))){
            column.setIsPrimaryKey("1");
          }else if("R".equalsIgnoreCase(rs.getString(11))){
            column.setIsForeignKey("1");
          }
        }
        columnList.add(column);
      }
    } catch (SQLException e) {
      throw new DAOException("Cannot to excute Oracle find getAllColumn by columnSql : "+columnSql, e);
    } finally{
      JDBCUtil.free(rs, pstmt, con);
    }
    return columnList;
  }




  
}
