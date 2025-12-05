package orinnetwork.javafilter8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orinnetwork.javafilter8.domain.FilterLog;

public interface FilterLogRepository extends JpaRepository<FilterLog, Long> {
}
