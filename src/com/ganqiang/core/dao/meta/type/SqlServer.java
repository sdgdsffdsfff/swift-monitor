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

public class SqlServer extends DBAdaptor
{
  private DataSourceInfo dataSourceInfo;
  
  public SqlServer(DataSourceInfo ds){
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
      case SQLSERVER_2000:
        sql = "select name from dbo.sysobjects where type='U' and name != 'dtproperties' order by name";
      case SQLSERVER_2005:
        sql = "select name from dbo.sysobjects where type='U' and name != 'dtproperties' order by name";
      case SQLSERVER_2008:
        sql = "select name from dbo.sysobjects where type='U' and name != 'dtproperties' order by name";
    } 
    return sql;    
  }

  
  String getSearchColumnSql(DataSourceInfo ds)
  {
    String sql = null;
    switch (ds.getDbVersion()) {
      case SQLSERVER_2000:
        sql = "select * from  (select a.id,a.colid,d.name table_name,a.name column_name,b.name column_type,a.length,a.cdefault,a.scale,a.isnullable "+
              "from syscolumns a,systypes b,sysobjects d where a.xtype=b.xusertype and a.id=d.id and d.type='U' and d.name != 'dtproperties' ) e "+
              "left join  (select id,colid,text from syscomments) i on i.id=e.cdefault " +
              "left join  (select id,colid from sysindexkeys ) f on e.id=f.id and e.colid=f.colid "+
              "left join  (select fkeyid,fkey from sysforeignkeys) g on g.fkeyid=e.id and e.colid=g.fkey "+
              "left join  (select id,smallid,cast(value as varchar) comment from sysproperties) h on h.id=e.id and h.smallid=e.colid";
      case SQLSERVER_2005:
        sql = "select * from  (select a.id,a.colid,d.name table_name,a.name column_name,b.name column_type,a.length,a.cdefault,a.scale,a.isnullable "+
              "from syscolumns a,systypes b,sysobjects d where a.xtype=b.xusertype and a.id=d.id and d.type='U' and d.name != 'dtproperties' ) e "+
              "left join  (select id,colid,text from syscomments) i on i.id=e.cdefault " +              
              "left join  (select id,colid from sysindexkeys ) f on e.id=f.id and e.colid=f.colid "+
              "left join  (select fkeyid,fkey from sysforeignkeys) g on g.fkeyid=e.id and e.colid=g.fkey "+
              "left join  (select id,smallid,cast(value as varchar) comment from sysproperties) h on h.id=e.id and h.smallid=e.colid";
      case SQLSERVER_2008:
        sql = "select * from  (select a.id,a.colid,d.name table_name,a.name column_name,b.name column_type,a.length,a.cdefault,a.scale,a.isnullable "+
              "from syscolumns a,systypes b,sysobjects d where a.xtype=b.xusertype and a.id=d.id and d.type='U' and d.name != 'dtproperties' ) e "+
              "left join  (select id,colid,text from syscomments) i on i.id=e.cdefault " +
              "left join  (select id,colid from sysindexkeys ) f on e.id=f.id and e.colid=f.colid "+
              "left join  (select fkeyid,fkey from sysforeignkeys) g on g.fkeyid=e.id and e.colid=g.fkey "+
              "left join  (select id,smallid,cast(value as varchar) comment from sysproperties) h on h.id=e.id and h.smallid=e.colid";
    } 
    return sql;
  }

  
  String getSearchConstraintSql(DataSourceInfo ds)
  {
    String sql = null;
    switch (ds.getDbVersion()) {
      case SQLSERVER_2000:
        sql = "select  fk.name as constraint_name,ft.name as table_name,fc.name as column_name,rt.name as referenced_table_name,rc.name as referenced_column_name "+
              "from sysobjects as fk, sysobjects as ft,sysobjects as rt,sysforeignkeys as r,syscolumns as rc,syscolumns as fc "+
              "where r.constid = fk.id and r.fkeyid = ft.id and r.rkeyid = rt.id and r.fkeyid = fc.id and r.rkeyid = rc.id "+
              "and ft.id = fc.id and rt.id = rc.id and r.fkey = fc.colid and r.rkey = rc.colid";
      case SQLSERVER_2005:
        sql = "select  fk.name as constraint_name,ft.name as table_name,fc.name as column_name,rt.name as referenced_table_name,rc.name as referenced_column_name "+
              "from sysobjects as fk, sysobjects as ft,sysobjects as rt,sysforeignkeys as r,syscolumns as rc,syscolumns as fc "+
              "where r.constid = fk.id and r.fkeyid = ft.id and r.rkeyid = rt.id and r.fkeyid = fc.id and r.rkeyid = rc.id "+
              "and ft.id = fc.id and rt.id = rc.id and r.fkey = fc.colid and r.rkey = rc.colid";
      case SQLSERVER_2008:
        sql = "select  fk.name as constraint_name,ft.name as table_name,fc.name as column_name,rt.name as referenced_table_name,rc.name as referenced_column_name "+
              "from sysobjects as fk, sysobjects as ft,sysobjects as rt,sysforeignkeys as r,syscolumns as rc,syscolumns as fc "+
              "where r.constid = fk.id and r.fkeyid = ft.id and r.rkeyid = rt.id and r.fkeyid = fc.id and r.rkeyid = rc.id "+
              "and ft.id = fc.id and rt.id = rc.id and r.fkey = fc.colid and r.rkey = rc.colid";
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
        column.setType(rs.getString(5));
        if(!StringUtil.isNullOrBlank(rs.getString(6))){
          column.setLength(rs.getInt(6));
        }
        if(!StringUtil.isNullOrBlank(rs.getString(8))){
          column.setPrecision(rs.getInt(8));
        }
        column.setNecessary("0".equalsIgnoreCase(rs.getString(9))?"1":"0");
        String defaultValue = rs.getString(12);//注意：默认值有可能是sql语句
        if(!StringUtil.isNullOrBlank(defaultValue)){
          defaultValue = defaultValue.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("'", "");
          if(200 >= defaultValue.length()){
            column.setDefaultValue(defaultValue);
          }else{
            column.setDefaultValue(defaultValue.substring(0,200));
          }
        }
        if(!StringUtil.isNullOrBlank(rs.getString(13))){
           column.setIsPrimaryKey("1");
        }
        if(!StringUtil.isNullOrBlank(rs.getString(15))){
          column.setIsForeignKey("1");
        }
        column.setComment(rs.getString(19));
        columnList.add(column);
      }
    } catch (SQLException e) {
      throw new DAOException("Cannot to excute SqlServer find getAllColumn by columnSql : "+columnSql, e.getCause());
    } finally{
      JDBCUtil.free(rs, pstmt, con);
    }
    return columnList;
  }

}
