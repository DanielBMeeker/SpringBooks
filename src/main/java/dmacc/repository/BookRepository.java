package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Books;

public interface BookRepository extends JpaRepository<Books, Long>{

}
