package JustBank;


import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import junit.framework.TestCase;

public class JBankTest extends TestCase{
	//Then configure the database to prescribe
	String path = "";
	String user = "";
	String password = "";
	private final Map jMap = new HashMap();
	private MyDB jSQL;
	@Before
	public void setUp() throws Exception
	{
		jSQL = new MyDB(path,user,password); 
		jMap.put("", "");
		jMap.put("root", "qaz");
		jMap.put("guest", "qwe");
		jMap.put("test", "test");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public void testCreateUser()
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.CreateUser(User, Password, Math.random());
		}
	}
	public void testGetMoney() throws SQLException
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.GetMoney(User, Password);
		}
	}
	public void testGetPassword() throws SQLException
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			Password.equals(jSQL.GetPassword(User));
		}
	}
	public void testDepositMoney() throws SQLException
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.DepositMoney(User, Password, Math.random());
		}
	}
	public void testTransferMoney() throws SQLException
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.TransferMoney(User, Password, User, Math.random());
		}
	}
	public void testDeleteUser()
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.DeleteUser(User, Password);
		}
	}

}
