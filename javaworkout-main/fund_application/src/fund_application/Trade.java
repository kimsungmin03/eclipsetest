package fund_application;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Trade {
    private final ExecutorService transactionLogger = 
        Executors.newSingleThreadExecutor();

    public void Buy_stock(User_info user, Stock_info stock, int amount) {
        int totalCost = stock.get_price() * amount;
        if (user.Get_balance() >= totalCost) {
            executeTransaction(user, stock, amount, totalCost, "BUY");
        } else {
            System.out.println("잔액 부족: " + user.Get_Id());
        }
    }

    public void Sell_stock(User_info user, Stock_info stock, int amount) {
        HashMap<String, Integer> ownedStocks = new CheckingStock().CheckStock(user);
        int ownedAmount = ownedStocks.getOrDefault(stock.Stock_name, 0);
        
        if (ownedAmount >= amount) {
            executeTransaction(user, stock, amount, 
                stock.get_price() * amount, "SELL");
        } else {
            System.out.println("보유 수량 부족: " + ownedAmount);
        }
    }

    private void executeTransaction(User_info user, Stock_info stock, 
                                   int amount, int total, String type) {
        updateUserBalance(user, total, type);
        updateStockHoldings(user, stock, amount, type);
        logTransactionAsync(user, stock, amount, type);
    }

    private void updateUserBalance(User_info user, int amount, String type) {
        if ("BUY".equals(type)) {
            user.set_balance(user.Get_balance() - amount);
        } else {
            user.set_balance(user.Get_balance() + amount);
        }
    }

    private void updateStockHoldings(User_info user, Stock_info stock, 
                                    int amount, String type) {
        int current = user.Owned_stock.getOrDefault(stock.Stock_name, 0);
        if ("BUY".equals(type)) {
            user.Owned_stock.put(stock.Stock_name, current + amount);
        } else {
            user.Owned_stock.put(stock.Stock_name, current - amount);
        }
    }

    private void logTransactionAsync(User_info user, Stock_info stock, 
                                    int amount, String type) {
    	transactionLogger.submit(() -> {
    	    try {
    	        User_info.UserStock history = user.new UserStock(
    	            user.get_time(),
    	            stock.Stock_name,
    	            amount,
    	            stock.get_price(),
    	            type
    	        );
    	        history.save();
    	    } catch (Exception e) {
    	        System.err.println("거래 기록 실패: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    	});
    }

    public void shutdown() {
        transactionLogger.shutdown();
    }
    
}
