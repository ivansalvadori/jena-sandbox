package jena.sandbox.sandbox;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;

public class OwlReasoner {

	public static void main(String[] args) throws IOException {

		final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream inputStream = contextClassLoader.getResourceAsStream("joao.rdf");
		String rdfString = IOUtils.toString(inputStream);
		StringReader sr = new StringReader(rdfString);
		model.read(sr, null, "N3");

		// imprimir todo o modelo
		model.write(System.out);

		// imprimir sujeitos
		ResIterator listSubjects = model.listSubjects();
		System.out.println("sujeitos:");
		while (listSubjects.hasNext()) {
			Resource next = listSubjects.next();
			System.out.println(next);
		}

		// imprimir objetos
		System.out.println("objetos:");
		NodeIterator listObjects = model.listObjects();
		while (listObjects.hasNext()) {
			RDFNode next = listObjects.next();
			System.out.println(next);
		}

		// imprimir joaozinho
		System.out.println("propriedades de joaozinho:");
		Resource joaozinho = model.getResource("http://exemplo.com/joaozinho");
		StmtIterator listProperties = joaozinho.listProperties();
		while (listProperties.hasNext()) {
			Statement next = listProperties.next();
			System.out.println(next);
		}

		InfModel pModel = ModelFactory.createInfModel(PelletReasonerFactory.theInstance().create(), model);

		// imprimir joaozinho
		System.out.println("propriedades de joao:");
		Resource joao = pModel.getResource("http://exemplo.com/joao");
		StmtIterator propriedadesJoao = joao.listProperties();
		while (propriedadesJoao.hasNext()) {
			Statement next = propriedadesJoao.next();
			System.out.println(next);
		}
	}

}
