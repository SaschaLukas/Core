package net.teamlife.lifecore.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MySQL {
	private String host;
	private int port;
	private String database;
	private String user;
	private String password;
	private Connection con;

	public MySQL(String host, int port, String database, String user, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.user = user;
		this.password = password;

		connect();
	}

	public Connection getCon() {
		return con;
	}
	

	public void connect() {
		try {
			this.con = DriverManager.getConnection(
					"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true",
					this.user, this.password);

			System.out.println("[MySQL] Connected to database");
		} catch (SQLException ex) {
			ex.printStackTrace();

			System.out.println("[MySQL] Could not connect");
		}
	}

	public void disconnect() {
		try {
			this.con.close();

			System.out.println("[MySQL] Disconnected from database");
		} catch (SQLException ex) {
			ex.printStackTrace();

			System.out.println("[MySQL] Could not disconnect");
		}
	}

	public void update(final String qry) {
		if (isConnected()) {
			new FutureTask(new Runnable() {
				PreparedStatement ps;

				@Override
				public void run() {
					try {
						this.ps = MySQL.this.con.prepareStatement(qry);

						this.ps.executeUpdate();
						this.ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}, Integer.valueOf(1)).run();
		} else {
			connect();
		}
	}

	@SuppressWarnings("unchecked")
	public void updateWithBoolean(final String qry, final boolean value) {
		if (isConnected()) {
			new FutureTask(new Runnable() {
				PreparedStatement ps;

				@Override
				public void run() {
					try {
						this.ps = MySQL.this.con.prepareStatement(qry);
						this.ps.setBoolean(1, value);

						this.ps.executeUpdate();
						this.ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}, Integer.valueOf(1)).run();
		} else {
			connect();
		}
	}

	public ResultSet getResult(final String qry) {
		if (isConnected()) {
			try {
				FutureTask<ResultSet> task = new FutureTask(new Callable() {
					PreparedStatement ps;

					@Override
					public ResultSet call() throws Exception {
						this.ps = MySQL.this.con.prepareStatement(qry);

						return this.ps.executeQuery();
					}
				});
				task.run();

				return task.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		} else {
			connect();
		}
		return null;
	}

	public boolean isConnected() {
		return this.con != null;
	}
}
