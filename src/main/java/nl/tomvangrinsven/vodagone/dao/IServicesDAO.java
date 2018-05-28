package nl.tomvangrinsven.vodagone.dao;

import nl.tomvangrinsven.vodagone.domain.Service;

import java.util.ArrayList;

public interface IServicesDAO {
    ArrayList<Service> getAll();
}
