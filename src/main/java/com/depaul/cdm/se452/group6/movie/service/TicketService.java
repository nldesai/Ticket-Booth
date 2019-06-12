package com.depaul.cdm.se452.group6.movie.service;

import com.depaul.cdm.se452.group6.movie.entity.Seat;
import com.depaul.cdm.se452.group6.movie.entity.Ticket;
import com.depaul.cdm.se452.group6.movie.finder.TicketRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TicketService {
	
	private TicketRepository ticketRepository;
	private LogService logService;
	private EntityManager entityManager;
	
	public TicketService(TicketRepository ticketRepository,
 						 						LogService logService,
											 EntityManager entityManager) {
		this.ticketRepository = ticketRepository;
		this.logService = logService;
		this.entityManager = entityManager;
	}

	@Transactional
	public Ticket createTicket(Ticket t) {
		try {
			entityManager.persist(t);
			entityManager.flush();
			ticketRepository.findBySeat(t.getSeat());
			return ticketRepository.findBySeat(t.getSeat());
		} catch (Exception e) {
			logService.logError("test_user", "create ticket");
			return null;
		}
	}
	
	public List<Ticket> getTickets() {
		 try {
			 List<Ticket> tickets = ticketRepository.findAll();
			 logService.logSuccess("test_user", "getAllTickets");
		     return tickets;
		 }	 catch (Exception e) {
			 logService.logError("test_user", "getAllTickets");
		     return null;
		     }
	}
	
	public List<Ticket> findTicketsByid(Long ticketid) {
		 try {
			 List<Ticket> tickets = ticketRepository.findByid(ticketid);
			 logService.logSuccess("test_user", "findByid" + ticketid);
		     return tickets;
		 }	 catch (Exception e) {
			 logService.logError("test_user", "findByid" + ticketid);
		     return null;
		     }
	}

	public void deleteTicket(Ticket ticket) {
		ticketRepository.delete(ticket);
	}
	
	public List<Ticket> findTicketsByUser(Seat seat) {
		 try {
			 List<Ticket> tickets = ticketRepository.findByUser(seat);
			 logService.logSuccess("test_user", "findByUser" + seat);
		     return tickets;
		 }	 catch (Exception e) {
			 logService.logError("test_user", "findByUser" + seat);
		     return null;
		     }
	}

}
