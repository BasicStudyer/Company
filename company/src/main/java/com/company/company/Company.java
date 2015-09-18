package com.company.company;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Company extends JavaPlugin {
	List<String> companyname = new ArrayList<String>();
	List<String> companynum = new ArrayList<String>();
	
	@Override
    public void onEnable(){
		
		try{
			this.reloadConfig();
			
			companyname = this.getConfig().getStringList("name");
	        companynum = this.getConfig().getStringList("number");
		}catch (Exception e){
			throw new CompanyDataException("������ ������ ���� �� �����ϴ�!");
		}
		getLogger().info("ȸ�� ���� �ε��");
		
    }
 
    @Override
    public void onDisable() {
    	try{
    		this.getConfig().set("name", companyname);
			this.getConfig().set("number", companynum);
			
			this.saveConfig();
    	}catch(Exception e){
    		throw new CompanyDataException("������ ���Ͽ� �� �� �����ϴ�!");
    	}
        getLogger().info("ȸ�� ���� �����");
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equals("addcompany")){
    		String CEO;
    		try {
    			if (args[1].equals(null)){
    				sender.sendMessage("����ڵ�Ϲ�ȣ�� �ʼ��Դϴ�.");
    				return true;
    			}
    			if (args[2].equals(null)){
    				CEO = sender.getName();
    			}else{
    				CEO = args[2];
    			}
    			
    			companyname.add(args[0]);
    			companynum.add(args[1]);
    			
    			this.getConfig().set("name", companyname);
    			this.getConfig().set("number", companynum);
    			this.getConfig().set("company." + args[0] + ".ceo", CEO);
    			
    			String a = args[0] + " (" + args[1] + ")";
        		sender.sendMessage(ChatColor.BLUE + a + " �� ����������ϴ�!");
        		getLogger().info(sender.getName() + " �� ȸ�� ����: " + a);
        		
        		Bukkit.broadcastMessage(ChatColor.BLUE + args[0] + "�̶�� ȸ�簡 ������!");
    		} catch (Exception e){
    			sender.sendMessage(e.getMessage());
    			sender.sendMessage("��, ���� �߸��� �� ���ƿ�.");
    		}

    		
    		return true;
    	}
    	
    	if(cmd.getName().equals("delcompany")){
    		try{
    			companynum.remove(companyname.indexOf(args[0]));
    			companyname.remove(args[0]);
    			
    			this.getConfig().set("name", companyname);
    			this.getConfig().set("number", companynum);
    			this.getConfig().set("company." + args[0] + ".ceo", null);
    			this.getConfig().set("company." + args[0] + ".member", null);
    			
    			sender.sendMessage(ChatColor.BLUE + args[0] + " �� ���ŵǾ����ϴ�!");
    			getLogger().info(sender.getName() + " �� ȸ�� ����: " + args[0]);
    		}catch (Exception e){
    			sender.sendMessage("��, ���� �߸��� �� ���ƿ�.");
    		}
    		
    		
    		return true;
    	}
    	
    	if(cmd.getName().equals("listcompany")){
    		sender.sendMessage(ChatColor.YELLOW + "ȸ�� ���: ");
    		try{
    			for (String s : companyname){
    				sender.sendMessage(s + " (" + companynum.get(companyname.indexOf(s)) + ")");
    			}
    		}catch(CommandException e){
    			sender.sendMessage(e.getMessage());
    			return true;
    		}
    		getLogger().info(sender.getName() + " �� ȸ�� ��� ��û.");
    		return true;
    	}
    	if(cmd.getName().equals("reloadcompany")){
    		this.reloadConfig();
    		companyname = this.getConfig().getStringList("name");
	        companynum = this.getConfig().getStringList("number");
    		sender.sendMessage(ChatColor.BLUE + "�ҷ����� �Ϸ�");
    		
    		this.saveConfig();
    		sender.sendMessage(ChatColor.BLUE + "���� �Ϸ�");
    		
    		return true;
    	}
    	if(cmd.getName().equals("savecompany")){
    		this.saveConfig();
    		sender.sendMessage(ChatColor.BLUE + "���� �Ϸ�");
    		
    		return true;
    	}
    	if(cmd.getName().equals("company")){
    		switch(args[0]){
    			case("addmember"):
    				List<String> member = new ArrayList<String>();
    				member = this.getConfig().getStringList("company." + args[1] + ".member");
    				member.add(args[1]);
    				this.getConfig().set("company." + args[1] + ".member", member);
    				break;
    			case("delmember"):
    				List<String> remember = new ArrayList<String>();
    				remember = this.getConfig().getStringList("company." + args[1] + ".member");
    				remember.remove(args[1]);
    				this.getConfig().set("company." + args[1] + ".member", remember);
    				break;
    		}
    	}
    	return false; 
    
    }
    
}
