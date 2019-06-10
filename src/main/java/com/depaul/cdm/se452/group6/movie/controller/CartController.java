package com.depaul.cdm.se452.group6.movie.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.depaul.cdm.se452.group6.movie.entity.AlcoholItem;
import com.depaul.cdm.se452.group6.movie.entity.Cart;
import com.depaul.cdm.se452.group6.movie.entity.Drink;
import com.depaul.cdm.se452.group6.movie.entity.Food;
import com.depaul.cdm.se452.group6.movie.entity.Ticket;
import com.depaul.cdm.se452.group6.movie.service.AlcoholService;
import com.depaul.cdm.se452.group6.movie.service.CartService;
import com.depaul.cdm.se452.group6.movie.service.DrinkService;
import com.depaul.cdm.se452.group6.movie.service.FoodService;
import com.depaul.cdm.se452.group6.movie.service.TicketService;

@Controller
public class CartController {
	private CartService cartService;
	private TicketService ticketService;
	private FoodService foodService;
	private DrinkService drinkService;
	private AlcoholService alcoholService;
	
	public CartController(CartService cartService
			, TicketService ticketService
			, FoodService foodService
			, DrinkService drinkService
			, AlcoholService alcoholService) {
		this.cartService = cartService;
		this.ticketService = ticketService;
		this.foodService = foodService;
		this.drinkService = drinkService;
		this.alcoholService = alcoholService;
	}
	
	@GetMapping("/cart")
	public String showCart(Model model) {
		List<Cart> cartList = cartService.getCartByUserId(1L);
		List<Ticket> ticketList = new ArrayList<Ticket>();
		Map<Food, Integer> foodMap = new HashMap<Food, Integer>();
		Map<Drink, Integer> drinkMap = new HashMap<Drink, Integer>();
		Map<AlcoholItem, Integer> alcoholMap = new HashMap<AlcoholItem, Integer>();
		Double ticketPrice = 0.0;
		Double foodPrice = 0.0;
		Double drinkPrice = 0.0;
		Double alcoholPrice = 0.0;
		Double total = 0.0;
		
		 
		for (Cart cart: cartList) {
			// store list of tickets in the list and total ticketprice 
			if (!cart.getTicketCart().isEmpty()) {
				for (Long tid : cart.getTicketCart()) {
					for (Ticket ticket : ticketService.findTicketsByid(tid)) {
						ticketList.add(ticket);
						Double price = ticket.getSeat().getSeatType().getPrice();
						ticketPrice += price;
					}
			    }
			}  
			
				
			// store food/quantity in map and total foodprice 
			if (!cart.getFoodCart().isEmpty()) {
				for (Map.Entry<Long,Integer> e : cart.getFoodCart().entrySet()) {
					Food food = foodService.getFoodById(e.getKey());
					
					if (foodMap.equals(null) || !foodMap.containsKey(food)) {
						foodMap.put(food, e.getValue());
					} else {
						foodMap.replace(food, e.getValue() + foodMap.get(food));
					}
				
			    	foodPrice += food.getPrice() * e.getValue();
			    }
			}
			
			// store drink/quantity in map and total drinkprice 
			if (!cart.getDrinkCart().isEmpty()) {
				for (Map.Entry<Long,Integer> e : cart.getDrinkCart().entrySet()) { 
					Drink drink = drinkService.getDrinkById(e.getKey());
					
					if (drinkMap.equals(null) || !drinkMap.containsKey(drink)) {
						drinkMap.put(drink, e.getValue());
					} else {
						drinkMap.replace(drink, e.getValue() + drinkMap.get(drink));
					}
					
			    	drinkPrice += drink.getPrice() * e.getValue();
			    } 
			}
			
			// store alcohol/quantity in map and total alcoholprice 
			if (!cart.getAlcoholCart().isEmpty()) {
				for (Map.Entry<Long,Integer> e : cart.getAlcoholCart().entrySet()) { 
					AlcoholItem alcohol = alcoholService.getAlcoholById(e.getKey());
					
					if (alcoholMap.equals(null) || !alcoholMap.containsKey(alcohol)) {
						alcoholMap.put(alcohol, e.getValue());
					} else {
						alcoholMap.replace(alcohol, e.getValue() + alcoholMap.get(alcohol));
					}
					
			    	alcoholPrice += alcoholService.getAlcoholById(e.getKey()).getPrice() * e.getValue();
				} 
			}
		}

		total = ticketPrice + foodPrice + drinkPrice + alcoholPrice;
		
		model.addAttribute("tickets", ticketList);
		model.addAttribute("prefood", foodMap);
		model.addAttribute("predrink", drinkMap);
		model.addAttribute("prealcohol", alcoholMap);
		model.addAttribute("total", total);
		
		return "cart";
	}
	
	@PostMapping("/cart")
	public String checkout() {
		return "redirect:/history";
	
	}

}
