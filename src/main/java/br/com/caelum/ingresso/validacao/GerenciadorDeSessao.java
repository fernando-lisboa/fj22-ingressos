package br.com.caelum.ingresso.validacao;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {
	
	private List<Sessao> sessoesDasala;
	
	public GerenciadorDeSessao(List<Sessao> sessoesDasala){
		this.sessoesDasala = sessoesDasala;
		
	}
	
	public boolean cabe(final Sessao sessaoAtual){
		Optional<Boolean> optionalCabe = sessoesDasala.stream()
				.map(s->horarioIsValido(s, sessaoAtual))
				.reduce(Boolean::logicalAnd);
		return optionalCabe.orElse(true);
	}
	
	private boolean horarioIsValido(Sessao sessaoExistente, Sessao sessaoAtual){
		LocalTime horarioSessao = sessaoExistente.getHorario();
		LocalTime horarioAtual = sessaoAtual.getHorario();
		
		boolean ehAntes = horarioAtual.isBefore(horarioSessao);
		
		if(ehAntes){		
			return sessaoAtual.getHorarioTermino().isBefore(horarioSessao);
			
		}else {
			return sessaoExistente.getHorarioTermino().isBefore(horarioAtual);
		}
		
	}
}
