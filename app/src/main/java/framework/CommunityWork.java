package framework;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class CommunityWork extends Contract {
    private static final String BINARY = "606060405260008054600160a060020a033316600160a060020a0319909116179055610912806100306000396000f3006060604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166312884e4481146100b35780632f42c0171461011c57806341c0e1b514610141578063580db9a9146101545780637b76923b1461016a57806394ebbb6b146101a45780639675c009146101c3578063bc4b336514610201578063c45f0a4014610218578063fabf596814610223578063fd1d5ea9146102c0575b600080fd5b61011a60048035600160a060020a03169060248035919060649060443590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965050843594602081013594506040013592506102d6915050565b005b341561012757600080fd5b61012f610404565b60405190815260200160405180910390f35b341561014c57600080fd5b61011a61040b565b341561015f57600080fd5b61012f600435610432565b341561017557600080fd5b610180600435610457565b60405180848152602001838152602001828152602001935050505060405180910390f35b6101af60043561047b565b604051901515815260200160405180910390f35b34156101ce57600080fd5b6101d96004356104a1565b6040519215158352901515602083015215156040808301919091526060909101905180910390f35b6101af600160a060020a03600435166024356104ce565b6101af600435610586565b341561022e57600080fd5b6102396004356105fa565b604051600160a060020a038216602082015260408082528190810184818151815260200191508051906020019080838360005b8381101561028457808201518382015260200161026c565b50505050905090810190601f1680156102b15780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34156102cb57600080fd5b6101af6004356106d9565b6000818152600160208190526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a038916178155908101869055600281018580516103289291602001906107af565b50600381018490554283016004820155600681018054600160ff19909116811762ffff00191690915560028054808301610362838261082d565b506000918252602082200184905587111590506103fb5760408051908101604052600160a060020a0388168152602081018790526005820180546103a98260018301610856565b815481106103b357fe5b90600052602060002090600202016000820151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03919091161781556020820151600190910155505b50505050505050565b6002545b90565b60005433600160a060020a039081169116141561043057600054600160a060020a0316ff5b565b600060028281548110151561044357fe5b90600052602060002090015490505b919050565b60009081526001602081905260409091209081015460038201546004909201549092565b600081815260016020526040812060040154829042101561049b57600080fd5b50919050565b60008181526001602052604090206006015460ff6101008204811691620100008104821691169193909250565b60008181526001602081905260408083209091018054349081019091559080519081016040908152600160a060020a038616825260208083018490526000868152600191829052919091206005018054909161052d9083908301610856565b8154811061053757fe5b90600052602060002090600202016000820151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101559050505092915050565b6000818152600160208190526040808320909101543391600160a060020a0383169180156108fc029151600060405180830381858888f19350505050156105f1576000838152600160208190526040909120600601805462ff0000191662010000179055915061049b565b6000915061049b565b610602610882565b600082815260016020818152604080842060068101546002918201805490956301000000909204600160a060020a03169486946101009383161593909302600019019091169290920491601f8301819004810201905190810160405280929190818152602001828054600181600116156101000203166002900480156106c95780601f1061069e576101008083540402835291602001916106c9565b820191906000526020600020905b8154815290600101906020018083116106ac57829003601f168201915b5050505050915091509150915091565b6000818152600160205260408120600401544211156106f757600080fd5b60008281526001602052604090206006015462010000900460ff16158015610736575060008281526001602081905260409091206006015460ff161515145b80156107595750600082815260016020526040902060060154610100900460ff16155b156107a757506000818152600160208190526040909120600601805476ffffffffffffffffffffffffffffffffffffffff0000001916630100000033600160a060020a031602179055610452565b506000610452565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107f057805160ff191683800117855561081d565b8280016001018555821561081d579182015b8281111561081d578251825591602001919060010190610802565b50610829929150610894565b5090565b81548183558181151161085157600083815260209020610851918101908301610894565b505050565b8154818355818115116108515760020281600202836000526020600020918201910161085191906108ae565b60206040519081016040526000815290565b61040891905b80821115610829576000815560010161089a565b61040891905b8082111561082957805473ffffffffffffffffffffffffffffffffffffffff19168155600060018201556002016108b45600a165627a7a723058204f67282a7569d39bc5f36d35dd51c21e8833980aad822ed327da276f54f9e4f20029";

    protected CommunityWork(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CommunityWork(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> projectDeployer(String _projectOwnerAddress, BigInteger _allocatedFunds, String _projectDescription, BigInteger _targetAmount, BigInteger _fundingDuration, BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "projectDeployer", 
                Arrays.<Type>asList(new Address(_projectOwnerAddress),
                new Uint256(_allocatedFunds),
                new Utf8String(_projectDescription),
                new Uint256(_targetAmount),
                new Uint256(_fundingDuration),
                new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> getProjectIdLength() {
        Function function = new Function("getProjectIdLength", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> kill() {
        Function function = new Function(
                "kill", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getAllProjectId(BigInteger _index) {
        Function function = new Function("getAllProjectId", 
                Arrays.<Type>asList(new Uint256(_index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple3<BigInteger, BigInteger, BigInteger>> getProjectValues(BigInteger _projectId) {
        final Function function = new Function("getProjectValues", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> endingProject(BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "endingProject", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Tuple3<Boolean, Boolean, Boolean>> getProjectStatus(BigInteger _projectId) {
        final Function function = new Function("getProjectStatus", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple3<Boolean, Boolean, Boolean>>(
                new Callable<Tuple3<Boolean, Boolean, Boolean>>() {
                    @Override
                    public Tuple3<Boolean, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<Boolean, Boolean, Boolean>(
                                (Boolean) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> addFunds(String _senderAddress, BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "addFunds", 
                Arrays.<Type>asList(new Address(_senderAddress),
                new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> onCompletion(BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "onCompletion", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Tuple2<String, String>> getProjectInfo(BigInteger _projectId) {
        final Function function = new Function("getProjectInfo", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> acceptProject(BigInteger _projectId) {
        Function function = new Function(
                "acceptProject", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<CommunityWork> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(CommunityWork.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<CommunityWork> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(CommunityWork.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static CommunityWork load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CommunityWork(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static CommunityWork load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CommunityWork(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
