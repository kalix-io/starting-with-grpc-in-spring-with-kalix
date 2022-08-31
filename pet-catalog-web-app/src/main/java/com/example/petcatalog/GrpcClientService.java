package com.example.petcatalog;

import com.example.PetCatalogRepositoryGrpc;
import com.example.PetRepositoryApi;
import com.example.domain.PetItemDomain;
import com.example.view.AvailablePetsGrpc;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class GrpcClientService {

    @Autowired
    ManagedChannel channel;

    public void add(String name, String loc) {
        PetCatalogRepositoryGrpc.PetCatalogRepositoryBlockingStub blockingStub =
                PetCatalogRepositoryGrpc.newBlockingStub(channel);
        blockingStub.add(PetRepositoryApi.PetItem.newBuilder().setPetId(String.valueOf(System.currentTimeMillis()))
                .setImage(loc).setName(name).build());
    }

    public PetRepositoryApi.PetItem getPetDetails(String id) {
        PetCatalogRepositoryGrpc.PetCatalogRepositoryBlockingStub blockingStub =
                PetCatalogRepositoryGrpc.newBlockingStub(channel);
        PetRepositoryApi.PetItem itemDetails = blockingStub.getDetails(PetRepositoryApi.PetItemId.newBuilder()
                .setPetId(id).build());
        return itemDetails;
    }

    public List<PetItemDomain.Pet> loadAllPets() {
        AvailablePetsGrpc.AvailablePetsBlockingStub stub =
                AvailablePetsGrpc.newBlockingStub(channel);
        List<PetItemDomain.Pet> pets= new ArrayList<>();
        Iterator<PetItemDomain.Pet> allPetsItr = stub.getAllPets(Empty.newBuilder().build());
        allPetsItr.forEachRemaining(pets::add);
        return pets;
    }
}
