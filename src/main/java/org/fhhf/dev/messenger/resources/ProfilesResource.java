package org.fhhf.dev.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fhhf.dev.messenger.model.Profile;
import org.fhhf.dev.messenger.service.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfilesResource {

	ProfileService profileService = new ProfileService();
	
	@GET
	public List<Profile> getProfiles() {
		return profileService.getAllProfiles();
	}
	
	@GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam ("profileName") String pName) {
		return profileService.getProfile(pName);
	}
	
	@POST
	public Profile addProfile(Profile profile) {
		return profileService.addProfile(profile);
	}
	
	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam ("profileName") String pName, Profile profile) {
		profile.setProfileName(pName);
		return profileService.updateProfile(profile);
	}
	
	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam ("profileName") String pName){
		profileService.removeProfile(pName);
	}
}
