package ua.univer.controller;

import ua.univer.model.account.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/accountDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123root@");
    }

    public List<Integer> getAccountIds() throws Exception {
        List<Integer> accountIds = new ArrayList<Integer>();
        Connection con = getConnection();
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("select id_ac from accounts");
        while (res.next()) {
            accountIds.add(res.getInt("id_ac"));
        }
        res.close();
        return accountIds;
    }

    public Account getAccountById(int id) throws Exception {
        Account account = null;
        Connection con = getConnection();
        PreparedStatement st = con
                .prepareStatement("select id_cl, sum, status " + "from accounts " + "where id_ac = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            account = new Account(id, rs.getInt("id_cl"), rs.getInt("sum"),
                    rs.getBoolean("status") ? AccountStatus.OPEN : AccountStatus.BLOCKED);
        }
        rs.close();
        return account;
    }

    public Account getAccountByClientId(int clientId) throws Exception {
        Account account = null;
        Connection con = getConnection();
        PreparedStatement st = con
                .prepareStatement("select id_ac, sum, status " + "from accounts " + "where id_cl = ?");
        st.setInt(1, clientId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            account = new Account(rs.getInt("id_ac"), clientId, rs.getInt("sum"),
                    rs.getBoolean("status") ? AccountStatus.OPEN : AccountStatus.BLOCKED);
        }
        rs.close();
        return account;
    }

    public void addAccount(Account account) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
                "insert into accounts " + "values (?, ?, ?, ?)");
        st.setInt(1, account.getId());
        st.setInt(2, account.getClientId());
        st.setInt(3, account.getSum());
        st.setString(4, account.getStatus().getDescription());

        st.executeUpdate();
    }

    public void setAccount(Account account) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = con
                .prepareStatement("update accounts " + "set id_cl= ?, sum= ?, status= ? " + "where id_ac = ?");
        st.setInt(1, account.getClientId());
        st.setInt(2, account.getSum());
        st.setBoolean(3, (account.getStatus().getDescription().equals("open")) ? true : false);
        st.setInt(4, account.getId());

        st.executeUpdate();
    }

    public void removeAccount(int id) throws Exception {
        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement("delete from accounts " + "where id_ac = ?");
        st.setInt(1, id);

        st.executeUpdate();
    }

    public void closeConection() {
    }

}
