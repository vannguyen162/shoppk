package com.developer.customer.client;

import org.springframework.data.repository.CrudRepository;

import com.developer.common.entity.customer.ClientInfo;

public interface ClientInformationRepository extends CrudRepository<ClientInfo, Integer> {

}
