package itacademy.s5t2.diceGame.businessLayer.service.interfaces;

public interface ServiceInterface {
	
	/*List<Object> getAllElements();	//tried including these to keep interface as generic, hard to implement correctly as a lot of casting. maybe optional was the way to go?
	Object saveElement(Object o);
	Object updateElement(long id, Object o);
	Object getById(long id);*/
	
	void deleteById(long id);
	
}
