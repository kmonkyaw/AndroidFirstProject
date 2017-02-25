package sg.edu.nus.iss.club;

import java.util.*;

import sg.edu.nus.iss.club.dao.BookingDAO;
import sg.edu.nus.iss.club.dao.BookingDAOImpl;
import sg.edu.nus.iss.club.dao.DBHelper;
import sg.edu.nus.iss.club.dao.FacilityDAO;
import sg.edu.nus.iss.club.dao.FacilityDAOImpl;
import sg.edu.nus.iss.club.dao.MemberDAO;
import sg.edu.nus.iss.club.dao.MemberDAOImpl;

public class Club {

    private int                         numMembers;
    private ArrayList<Member>           members;
    private ArrayList<Facility>			facilities1;
    private ArrayList<Booking>			bookings1;
    private HashMap<String, Facility> facilities;
    private BookingRegister             bookings;

    private DBHelper dbHelper;
    private BookingDAO bookingDAO;
    private FacilityDAO facilityDAO;
    private MemberDAO memberDAO;
    
    public Club () {
        numMembers = 0;
        members = new ArrayList<Member> ();
        facilities = new HashMap<String, Facility> ();
        bookings = new BookingRegister ();        
   }

    public Club (int numMembers) {
        this.numMembers = numMembers; 
        this.members = new ArrayList<Member> ();
        this.facilities = new HashMap<String, Facility> ();
        this.bookings = new BookingRegister ();
    }
    
    public Member getMember (int memberNum) {
        Iterator<Member> i = members.iterator ();
        while (i.hasNext ()) {
            Member m = i.next();
            if (m.getMemberNumber() == memberNum) {
                return m;
            }
        }
        return null;
    }

    public List<Member> getMembers () {
    	List<Member> result;
        //result = new ArrayList<Member>(members);
    	members = new ArrayList<Member>();
    	
    	
    	memberDAO = new MemberDAOImpl();
    	members = (ArrayList<Member>) memberDAO.findAll();
        memberDAO.closeConnection();
        
    	
        if (members != null)
        	Collections.sort (members);
        else
        	members = new ArrayList<Member>();
        
        return (members);
    }
    
    public ArrayList<Booking> getBookings (String facName) {
        return bookings.getBookings (getFacility (facName));
    }

    public Member addMember (String surname, String firstName,
                                             String secondName) {
        numMembers++;
        Member m = new Member (surname, firstName, secondName,
                                                   numMembers);
        //members.add (m);
        
        memberDAO = new MemberDAOImpl();
        memberDAO.insert(m);
        memberDAO.closeConnection();
        return m;
    }

    protected Member addMember (int memberNum, String surname, 
                           String firstName, String secondName) {
        Member m = new Member (surname, firstName, secondName, memberNum);
        members.add (m);
        return m;
    }
    
    public void removeMember (int memberNum) {
//        Member m = getMember (memberNum);
//        if (m != null) {
//            members.remove (m);
            
            memberDAO = new MemberDAOImpl();
            memberDAO.delete(memberNum);
            memberDAO.closeConnection();
//        }
    }

    public void showMembers () {
        Iterator<Member> i = members.iterator ();
        while (i.hasNext ()) {
            i.next().show ();
        }
    }


    public Facility getFacility (String name) {
        return facilities.get (name);
    }
    
    public Facility getFacility (int facilityNum) {
        Iterator<Facility> i = facilities1.iterator ();
        while (i.hasNext ()) {
        	Facility m = i.next();
            if (m.getFacilityNumber() == facilityNum) {
                return m;
            }
        }
        return null;
    }

    public List<Facility> getFacilities () {
    	List<Facility> result;
        //result = new ArrayList<Facility>(facilities.values());
        
    	facilityDAO = new FacilityDAOImpl();
        facilities1 = (ArrayList<Facility>) facilityDAO.findAll();
        facilityDAO.closeConnection();
        
        Collections.sort (facilities1);
        return (facilities1);
    }

    public void addFacility (String name, String description) {
        if (name == null) {
            return;
        }
        Facility f = new Facility (name, description);
        //facilities.put (name, f);
        
        facilityDAO = new FacilityDAOImpl();
        facilityDAO.insert(f);
        facilityDAO.closeConnection();
    }

    public void removeFacility (String name) {
        facilities.remove (name);
    }
    
    public void removeFacility (int facilityNum) {
//    	Facility f = getFacility (facilityNum);
//        if (f != null) {
//            facilities1.remove (f);
            
            facilityDAO = new FacilityDAOImpl();
            facilityDAO.delete(facilityNum);
            facilityDAO.closeConnection();
//        }
    }

    public void showFacilities () {
        Iterator<Facility> i = getFacilities().iterator ();
        while (i.hasNext ()) {
            i.next().show ();
        }
    }


    /*
    public void addBooking (int memberNumber, String facName, Date startDate, Date endDate)
				throws BadBookingException {
        bookings.addBooking (getMember (memberNumber), getFacility (facName), startDate, endDate);
    }
    */

    public void addBooking (int memberNumber, int facNumber, Date startDate, Date endDate)
				throws BadBookingException {
        //bookings.addBooking (getMember (memberNumber), getFacility (facNumber), startDate, endDate);
        
    	Booking book = new Booking(getMember (memberNumber), getFacility (facNumber), startDate, endDate);
    	
        bookingDAO = new BookingDAOImpl();
        bookingDAO.insert(book);
        bookingDAO.closeConnection();
    }

    
    public void removeBooking (Booking booking) {
        //bookings.removeBooking (booking);
    	
        bookingDAO = new BookingDAOImpl();
        bookingDAO.delete(booking.getBookingNumber());
        bookingDAO.closeConnection();
    }

    public ArrayList<Booking> getBookings (String facName, Date startDate, Date endDate) {
    	return bookings.getBookings (getFacility (facName), startDate, endDate);
    }
    
    public ArrayList<Booking> getBookings (int facNumber, Date startDate, Date endDate) {
    	//return bookings.getBookings (getFacility (facNumber), startDate, endDate);
    	
    	List<Booking> result;
        //result = new ArrayList<Facility>(facilities.values());
        
    	bookingDAO = new BookingDAOImpl();
    	result = (ArrayList<Booking>) bookingDAO.findAll();
        bookingDAO.closeConnection();
        
        //Collections.sort (result);
        return (ArrayList<Booking>) (result);
    }

    public void showBookings (String facName, Date startDate, Date endDate) {
    	ArrayList<Booking> b = getBookings (facName, startDate, endDate);
    	Iterator<Booking> i = b.iterator();
        while (i.hasNext()) {
            i.next().show();
        }
    }
    protected int getNumMembers () {
        return numMembers;
    }

    public void show () {
        System.out.println ("Current Members:");
        showMembers ();
        System.out.println ();
        System.out.println ("Facilities:");
        showFacilities ();
    }
}
