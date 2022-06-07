package com.parkingmanagementsystem.demo.controller;

import com.parkingmanagementsystem.demo.model.entity.CustomerDetails;
import com.parkingmanagementsystem.demo.model.entity.ParkingLot;
import com.parkingmanagementsystem.demo.model.entity.ParkingSlotReservation;
import com.parkingmanagementsystem.demo.model.entity.User;
import com.parkingmanagementsystem.demo.repository.CustomerRepository;
import com.parkingmanagementsystem.demo.repository.userrepository.UserRepository;
import com.parkingmanagementsystem.demo.service.CustomerDetailsService;
import com.parkingmanagementsystem.demo.service.ParkingLotService;
import com.parkingmanagementsystem.demo.service.ParkingSlotReservationService;
import com.parkingmanagementsystem.demo.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UIController {


	@Autowired
	ParkingSlotReservationService parkingSlotReservationService;

	@Autowired
	ParkingLotService parkingLotService;

	@Autowired
	CustomerDetailsService customerDetailsService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	UserRepository userRepository;


	@GetMapping("/")
	public String root(Model model)
	{
		Authentication authentication= userService.getCurrentUser();
		model.addAttribute("username",authentication.getName());
		User user=userRepository.findByEmail(authentication.getName());
		model.addAttribute("user",user);
		return "index";
	}
	@GetMapping("/login")
	public String LoginPage()
	{

		return "login";
	}

	@GetMapping("/parkingLot")
	public String displayParkingLot(Model model) {
		ParkingLot parkingLot = new ParkingLot();
		model.addAttribute("parkingLot", parkingLot);
		return "parking_Lot";
	}

	@PostMapping("/parkinglot")
	public String lotCreated(@ModelAttribute("parkingLot") ParkingLot parkingLot) {
		parkingLotService.createParkingLot(parkingLot);
		return "Lot_created";
	}

	@GetMapping("/getParkingAvailable")
	public String getEmptyParkingLots(Model model) {
		List<ParkingLot> result = new ArrayList<>();
		parkingLotService.getParkingLots().forEach(result::add);
		result = result.stream().filter(parking -> parking.isEmpty()).collect(Collectors.toList());
		model.addAttribute("result", result);
		return "get_slot";
	}

	@GetMapping("/getpark")
	public String lotCreated(Model model) {
		CustomerDetails customerDetails1 = new CustomerDetails();
		model.addAttribute("customerDetails", customerDetails1);
		return "get_park";
	}

	@GetMapping("/getLotDetails")
	public String LotDetails() {
		return "get_Parking";
	}

	@GetMapping("/customer/details")
	public String getCustomerDetails(Model model) {
		CustomerDetails customerDetails = new CustomerDetails();
		model.addAttribute("customerDetails", customerDetails);
		return "customer_details";
	}

	@PostMapping("/customer/details")
	public String lotCreated(@ModelAttribute("customerDetails") CustomerDetails customerDetails, Model model) {
		ParkingSlotReservation parkingSlotReservation = parkingSlotReservationService.createParking(customerDetails);
		model.addAttribute("parkingSlotReservation", parkingSlotReservation);
		return "getTicket";
	}

}
