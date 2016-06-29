package jena.sandbox.sandbox;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.AllValuesFromRestriction;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;

public class OwlRestrictionReasoner {

	public static void main(String[] args) throws IOException {

		final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream inputStream = contextClassLoader.getResourceAsStream("brasileiroAssassinado.rdf");
		String rdfString = IOUtils.toString(inputStream);
		StringReader sr = new StringReader(rdfString);
		model.read(sr, null, "N3");

		InfModel pModel = ModelFactory.createInfModel(PelletReasonerFactory.theInstance().create(), model);

		// imprimir todo o modelo
		//pModel.write(System.out);

		ResIterator listSubjects = pModel.listSubjects();
		while(listSubjects.hasNext()){
			Resource next = listSubjects.next();
			//System.out.println(next);
		}
		
		
		NodeIterator listObjectsOfProperty = pModel.listObjectsOfProperty(ResourceFactory.createResource("http://dbpedia.org/ontology/birthPlace"), RDF.type);
		while(listObjectsOfProperty.hasNext()){
			System.out.println(listObjectsOfProperty.next());
		}
		
		ExtendedIterator<Restriction> listRestrictions = model.listRestrictions();
		while(listRestrictions.hasNext()){
			Restriction next = listRestrictions.next();
						
			boolean hasValueRestriction = next.isHasValueRestriction();
			
			System.out.println(next.getOnProperty());
		}
		
		
	}

}
