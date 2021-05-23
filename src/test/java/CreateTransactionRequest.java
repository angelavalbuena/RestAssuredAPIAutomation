public class CreateTransactionRequest{
	private String lastName;
	private String transactionType;
	private boolean active;
	private String email;
	private String telephone;
	private String amount;
	private String name;
	private String country;
	private String id;
	private String accountNumber;



	public CreateTransactionRequest(String id,String name,String lastName,String accountNumber,
									String amount, String transactionType, String email, boolean active,
									String country, String telephone) {
		this.lastName = lastName;
		this.transactionType = transactionType;
		this.active = active;
		this.email = email;
		this.telephone = telephone;
		this.amount = amount;
		this.name = name;
		this.country = country;
		this.id = id;
		this.accountNumber = accountNumber;
	}
	public CreateTransactionRequest(){};

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setTransactionType(String transactionType){
		this.transactionType = transactionType;
	}

	public String getTransactionType(){
		return transactionType;
	}

	public void setActive(boolean active){
		this.active = active;
	}

	public boolean isActive(){
		return active;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTelephone(String telephone){
		this.telephone = telephone;
	}

	public String getTelephone(){
		return telephone;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber(){
		return accountNumber;
	}
}
