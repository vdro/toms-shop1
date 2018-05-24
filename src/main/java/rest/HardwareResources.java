package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Hardware;

@Path("/hardware")
@Stateless
public class HardwareResources {

	@PersistenceContext
	EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hardware> getAll() {
		return em.createNamedQuery("hardware.all", Hardware.class).getResultList();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Hardware hardware) {
		em.persist(hardware);
		return Response.ok("added entity").build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Hardware result = em.createNamedQuery("hardware.id", Hardware.class)
				.setParameter("hardwareId", id)
				.getSingleResult();
		if(result==null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Hardware h) {
		Hardware result = em.createNamedQuery("hardware.id", Hardware.class)
				.setParameter("hardwareId", id)
				.getSingleResult();
		if (result==null) {
			return Response.status(404).build();
		}
		result.setName(h.getName());
		result.setCategory(h.getCategory());
		result.setPriceMin(h.getPriceMin());
		result.setPriceMax(h.getPriceMax());
		em.persist(result);
		return Response.ok("updated entity").build();
		
	}
	
	@GET
	@Path("/find/{priceMin}/{priceMax}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hardware> getPrice(@PathParam("priceMin") float priceMin, @PathParam("priceMax") float priceMax) {
		 List<Hardware> result = em.createNamedQuery("hardware.price", Hardware.class)
				.setParameter("hardwarePriceMin", priceMin)
				.setParameter("hardwarePriceMax", priceMax)
				.getResultList();
		 return result;
	}
	
	@GET
	@Path("/find/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByName(@PathParam("name") String name){
		Hardware result = em.createNamedQuery("hardware.name", Hardware.class)
				.setParameter("hardwareName", name)
				.getSingleResult();
		if(result==null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	@GET
	@Path("/find/graphics")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hardware> getGraphics() {
		return em.createNamedQuery("hardware.category", Hardware.class).
				setParameter("hardwareCategory", "graphics")
				.getResultList();
	}
	
	@GET
	@Path("/find/mainboards")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hardware> getMainboards() {
		return em.createNamedQuery("hardware.category", Hardware.class).
				setParameter("hardwareCategory", "mainboards")
				.getResultList();
	}
	
	@GET
	@Path("/find/discs")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hardware> getDiscs() {
		return em.createNamedQuery("hardware.category", Hardware.class).
				setParameter("hardwareCategory", "discs")
				.getResultList();
	}
	
	@GET
	@Path("/find/ram")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hardware> getRam() {
		return em.createNamedQuery("hardware.category", Hardware.class).
				setParameter("hardwareCategory", "ram")
				.getResultList();
	}
	
	@POST
	@Path("/comment/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setComment(@PathParam("id") int id, Comment comment) {
		Hardware result = em.createNamedQuery("hardware.id", Hardware.class)
							.setParameter("hardwareId", id)
							.getSingleResult();
		if(result==null) {
			return Response.status(404).build();
		}
		result.getComment().add(comment);
		comment.setHardware(result);
		em.persist(comment);
		return Response.ok("added comment").build();
	}
	
	@GET
	@Path("/comment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComment(@PathParam("id") int hardwareId) {
		Hardware result = em.createNamedQuery("hardware.id", Hardware.class)
							.setParameter("hardwareId", hardwareId)
							.getSingleResult();
		if(result==null) {
			return null;
		}
		return result.getComment();
	}
	
	@DELETE
	@Path("/comment/{id}")
	public Response deleteComment(@PathParam("id") int id) {
		
		Hardware hardware = em.createNamedQuery("hardware.id", Hardware.class)
							.setParameter("hardwareId", id)
							.getSingleResult();
		if(hardware==null) {
			return Response.status(404).build();
		}
		
		List<Comment> comment = em.createNamedQuery("comment.id", Comment.class)
								.getResultList();
		if(comment==null) {
			return Response.status(404).build();
		}
		
		for(Comment c : comment) {
			if(c.getHardware().getId()==hardware.getId()) {
				em.remove(c);
			}
		}
		return Response.ok("deleted comments").build();
	}
	
 }
