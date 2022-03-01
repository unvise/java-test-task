package com.unvise.transportbook.repository;

import com.unvise.transportbook.entity.Transport;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long>, JpaSpecificationExecutor<Transport> {

    @Query("SELECT t FROM Transport t")
    List<Transport> findAllTransport();

    @NonNull
    @Query("SELECT t FROM Transport t WHERE t.id=?1")
    Optional<Transport> findById(@NonNull Long id);

    @Query("SELECT t FROM Transport t WHERE t.brand=?1")
    List<Transport> findAllByBrand(@NonNull String brand);

    @Query("SELECT t FROM Transport t WHERE t.model=?1")
    List<Transport> findAllByModel(@NonNull String model);

    @Query("SELECT t FROM Transport t WHERE t.category=?1")
    List<Transport> findAllByCategory(@NonNull Transport.Category transportCategory);

    @Query("SELECT t FROM Transport t WHERE t.licensePlate=?1")
    List<Transport> findAllByLicensePlate(@NonNull String licensePlate);

    @Query("SELECT t FROM Transport t WHERE t.type=?1")
    List<Transport> findAllByTransportType(@NonNull Transport.Type transportType);

    @Query("SELECT t FROM Transport t WHERE t.prodYear=?1")
    List<Transport> findAllByProdYear(@NonNull Date prodYear);

    @Query("SELECT t FROM Transport t WHERE t.withTrailer=?1")
    List<Transport> findAllByTrailer(@NonNull Boolean withTrailer);
}
