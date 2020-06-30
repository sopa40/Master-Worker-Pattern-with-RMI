package mwp;

/**
 * Task 1.4
 * @author Qianli Wang und Nazar Sopiha 
 */

public class TaskMath implements Task<Integer, Integer>{

	/**
	 * default generated serialVersionUID
	 */
	private static final long serialVersionUID = -818526119449533047L;
	
	public TaskMath() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public Integer exec(Integer a) {
		// TODO Auto-generated method stub
		return (Integer)(a * a);
	}

}
