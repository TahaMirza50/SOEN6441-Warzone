package model.order;

import model.Country;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * A class to create Orders in the game.
 * @author Poorav Panchal
 *  @author  Shariq Anwar
 *  @author Pruthvirajsinh Dodiya
 */
public class OrderCreator implements Serializable {
    /**
     * Static object of Game Map to hold instance of game map
     */
    public static GameMap d_GameMap = GameMap.getInstance();

    /**
     * Logger instance
     */
    private static LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * A function to create an order
     *
     * @param p_Commands the command entered
     * @param p_Player   object parameter of type Player
     * @return the order
     */
    public static Order CreateOrder(String[] p_Commands, Player p_Player) {
        String l_Type = p_Commands[0].toLowerCase();
        Order l_Order;
        switch (l_Type) {
            case "deploy":
                l_Order = new DeployOrder();
                l_Order.setOrderInfo(GenerateDeployOrderInfo(p_Commands, p_Player));
                break;
            case "advance":
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(GenerateAdvanceOrderInfo(p_Commands, p_Player));
                break;
            case "negotiate":
                l_Order = new NegotiateOrder();
                l_Order.setOrderInfo(GenerateNegotiateOrderInfo(p_Commands, p_Player));
                break;
            case "blockade":
                l_Order = new BlockadeOrder();
                l_Order.setOrderInfo(GenerateBlockadeOrderInfo(p_Commands, p_Player));
                break;
            case "airlift":
                l_Order = new AirliftOrder();
                l_Order.setOrderInfo(GenerateAirliftOrderInfo(p_Commands, p_Player));
                break;
            case "bomb":
                l_Order = new BombOrder();
                l_Order.setOrderInfo(GenerateBombOrderInfo(p_Commands, p_Player));
                break;
            default:
                d_Logger.log("\nFailed to create an order due to invalid arguments");
                l_Order = null;

        }
        return l_Order;
    }

    /**
     * A function to generate the information of Deploying the order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderInfo GenerateDeployOrderInfo(String[] p_Command, Player p_Player) {
        Country l_Country = d_GameMap.getCountry(p_Command[1]);
        int l_NumberOfArmies = Integer.parseInt(p_Command[2]);
        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setCommand(ConvertToString(p_Command));
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setDestination(l_Country);
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmies);
        if(p_Player.getReinforcementArmies() > 0 && l_NumberOfArmies <= p_Player.getD_ArmiesToIssue() && l_NumberOfArmies > 0){
            p_Player.setD_ArmiesToIssue(p_Player.getD_ArmiesToIssue() - l_NumberOfArmies);
        } else {
            d_Logger.log("\nYou do not have enough armies left to deploy.\n" +
                    "The order would be discarded during execution phase.\n");
        }
        return l_OrderInfo;
    }

    /**
     * A function to generate the information for advance order
     *
     * @param p_Command the command entered
     * @param p_Player  the player who issued the order
     * @return the order information of advance/attack
     */
    public static OrderInfo GenerateAdvanceOrderInfo(String[] p_Command, Player p_Player) {
        String l_FromCountryID = p_Command[1];
        Country l_FromCountry = d_GameMap.getCountry(l_FromCountryID);
        String l_ToCountryID = p_Command[2];
        Country l_ToCountry = d_GameMap.getCountry(l_ToCountryID);
        int l_NumberOfArmies = Integer.parseInt(p_Command[3]);
        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setCommand(ConvertToString(p_Command));
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setDeparture(l_FromCountry);
        l_OrderInfo.setDestination(l_ToCountry);
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmies);
        return l_OrderInfo;
    }

    /**
     * A function to generate the information of Negotiate order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderInfo GenerateNegotiateOrderInfo(String[] p_Command, Player p_Player) {
        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setCommand(ConvertToString(p_Command));
        l_OrderInfo.setNeutralPlayer(d_GameMap.getPlayer(p_Command[1]));
        return l_OrderInfo;
    }

    /**
     * A function to generate information about Blockade Order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderInfo GenerateBlockadeOrderInfo(String[] p_Command, Player p_Player) {
        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setCommand(ConvertToString(p_Command));
        l_OrderInfo.setPlayer(p_Player);
        String l_CountryID = p_Command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderInfo.setTargetCountry(l_TargetCountry);
        return l_OrderInfo;
    }

    /**
     * function to generate information about Airlift Order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information of deploy
     */
    public static OrderInfo GenerateAirliftOrderInfo(String[] p_Command, Player p_Player) {
        String l_FromCountryID = p_Command[1];
        Country l_FromCountry = d_GameMap.getCountry(l_FromCountryID);
        String l_ToCountryID = p_Command[2];
        Country l_ToCountry = d_GameMap.getCountry(l_ToCountryID);
        int l_NumberOfArmies = Integer.parseInt(p_Command[3]);
        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setCommand(ConvertToString(p_Command));
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setDeparture(l_FromCountry);
        l_OrderInfo.setDestination(l_ToCountry);
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmies);
        return l_OrderInfo;
    }

    /**
     * function to generate information about Bomb Order
     *
     * @param p_Command the command entered
     * @param p_Player  object parameter of type Player
     * @return the order information
     */
    public static OrderInfo GenerateBombOrderInfo(String[] p_Command, Player p_Player) {
        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setCommand(ConvertToString(p_Command));
        l_OrderInfo.setPlayer(p_Player);
        String l_CountryID = p_Command[1];
        Country l_TargetCountry = d_GameMap.getCountry(l_CountryID);
        l_OrderInfo.setTargetCountry(l_TargetCountry);
        return l_OrderInfo;
    }


    /**
     * The method to convert command to string
     *
     * @param p_Commands the command entered
     * @return the string
     */
    private static String ConvertToString(String[] p_Commands) {
        StringJoiner l_Joiner = new StringJoiner(" ");
        for (String pCommand : p_Commands) {
            l_Joiner.add(pCommand);
        }
        return l_Joiner.toString();
    }

}
