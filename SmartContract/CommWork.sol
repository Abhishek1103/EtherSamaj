
pragma solidity ^0.4.19;

/**
 * The mortal contract contains the suicide method
 */
contract mortal 
{
	address owner;		// A variable for address type to store address of owner of contract
	
	modifier onlyOwner()
	{ // Modifier for onlyOwner
		if (msg.sender == owner)
		_; 
	}
	
	function mortal() public 
	{		// Constructor
		owner = msg.sender;	
	}

	function kill () onlyOwner 
	{	// Function to kill the contract
		selfdestruct(owner);
	}
	
}


/**
 * The CommunityWork contract is one of the three main contracts
 */
contract CommunityWork is mortal
{
	function CommunityWork () payable 
	{
		
	}	

    // project type...containing all the required details of project
	struct project {
		address projectOwnerAddress;
		uint allocatedFunds;
		string projectDescription;
		uint targetAmount;
		uint fundingDuration;
		funders[] arrayOfFunders;
		bool isOpen;
		bool isAccepted;
		bool isCompleted;
		address projectAcceptor;
	}
	
	struct funders{
	    address funderAddress;
	    uint amountFunded;
	}

	mapping (uint => project) projectIdToProject;
	uint[] projectIdArray;
	
	//Modifier for time keeping stuff
	modifier afterTimeLimit(uint _projectId)
	{
	    require(now >= projectIdToProject[_projectId].fundingDuration);
	    _;
	}
	
	
	// Function to create a new project
	function projectDeployer(
	    address _projectOwnerAddress, 
	    uint _allocatedFunds, 
	    string _projectDescription,
	    uint _targetAmount,
	    uint _fundingDuration,
	    uint _projectId) payable{
	    
	    var projectInfo = projectIdToProject[_projectId];
	    projectInfo.projectOwnerAddress = _projectOwnerAddress;
	    projectInfo.allocatedFunds = _allocatedFunds;
	    projectInfo.projectDescription = _projectDescription;
	    projectInfo.targetAmount = _targetAmount;
	    projectInfo.fundingDuration = now + _fundingDuration;
	    projectInfo.isOpen = true;
	    projectInfo.isAccepted = false;
	    projectInfo.isCompleted = false;
	    
	    projectIdArray.push(_projectId) -1;
	    
	    if(_allocatedFunds > 0){
	       // funders storage funder0;
	       // funder0.funderAddress = _projectOwnerAddress;
	       // funder0.amountFunded = _allocatedFunds;
	       // projectInfo.arrayOfFunders.push(funder0) -1;
	       projectInfo.arrayOfFunders[projectInfo.arrayOfFunders.length++] = funders({funderAddress: _projectOwnerAddress, amountFunded: _allocatedFunds});
	    }
	}
	
	// Function to get allProjectIds
	function getAllProjectId() view public returns(uint[]){
	    return projectIdArray;
	}
	
	// Function to return details of a project coressponding to an ID
	function getProjectInfo(uint _projectId)view public returns(project){
	    return(projectIdToProject[_projectId]);
	}
	
	// Function which accepts new funds on a particular project
	function addFunds(address _senderAddress, uint _projectId) payable returns(bool){
	   /* Check if project is alive and funding is on */
	   uint amount = msg.value;
        projectIdToProject[_projectId].allocatedFunds += amount;
	   projectIdToProject[_projectId].arrayOfFunders[projectIdToProject[_projectId].arrayOfFunders.length++] = funders ({funderAddress: _senderAddress, amountFunded: amount});
	}
	
	// Function for accepting projects
	function acceptProject(uint _projectId) returns(bool){
	    
	    require(now <= projectIdToProject[_projectId].fundingDuration);
	    
	    if(projectIdToProject[_projectId].isCompleted == false &&
	    projectIdToProject[_projectId].isOpen == true &&
	    projectIdToProject[_projectId].isAccepted == false){
	        
	        projectIdToProject[_projectId].isAccepted == true;
	        projectIdToProject[_projectId].projectAcceptor = msg.sender;
	        return true;
	    }else {
	        // Error in accepting project
	        throw;
	        return false;
	    }
	}
	//Function for completion of project
	//****Add check for time interval
	function onCompletion(uint _projectId) payable returns(bool){
	    address fundAcceptor = msg.sender;
	    if(fundAcceptor.send(projectIdToProject[_projectId].allocatedFunds)){
	        projectIdToProject[_projectId].isCompleted = true;
	        return true;
	    }else {
	        return false;
	    }
	}
	
	//Time limit event for each projectIdToProject element
	
	//Function for money back if time limit exceeded controled by modifier
	function endingProject(uint _projectId) afterTimeLimit(_projectId) payable returns(bool){
	    
	}
}