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
		//jMap.put("", "");
		jMap.put("root", "qaz");
		jMap.put("guest", "qwe");
		jMap.put("test", "test");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public void testCreateUser() throws SQLException
	{
		double i=100;
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.CreateUser(User, Password, i);
			assertEquals(i,jSQL.GetMoney(User, Password));
			i+=i;
		}
	}
	public void testGetMoney() throws SQLException
	{
		double i=100;
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			assertEquals(i,jSQL.GetMoney(User, Password));
			i+=i;
		}
	}
	public void testGetPassword() throws SQLException
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			assertEquals(Password,jSQL.GetPassword(User));
		}
	}
	public void testDepositMoney() throws SQLException
	{
		double i=100;
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.DepositMoney(User, Password, i);
			assertEquals(i*2,jSQL.GetMoney(User, Password));
			i+=i;
		}
	}
	public void testWithdrawalMoney() throws SQLException
	{
		double i=100;
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.DepositMoney(User, Password, -i);
			assertEquals(i,jSQL.GetMoney(User, Password));
			i+=i;
		}
	}
	public void testTransferMoney() throws SQLException
	{
		double i=100;
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.TransferMoney(User, Password, User, i);
			assertEquals(i,jSQL.GetMoney(User, Password));
			i+=i;
		}
	}
	public void testDeleteUser() throws SQLException
	{
		for (Iterator iterator = jMap.keySet().iterator();iterator.hasNext();)
		{
			final String User = (String)iterator.next();
			final String Password = (String)jMap.get(User); 
			jSQL.DeleteUser(User, Password);
			assertEquals("",jSQL.GetPassword(User));
		}
	}

}
