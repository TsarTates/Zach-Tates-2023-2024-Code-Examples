using UnityEngine;

namespace CSE5912.PenguinProductions
{
    public class KiwiState_GoHome : IState
    {
        private readonly KiwiReferences kiwiReferences;
        private readonly RoomPosition homeBase;

        public KiwiState_GoHome(KiwiReferences kiwiReferences, RoomPosition homeBase)
        {
            this.kiwiReferences = kiwiReferences;
            this.homeBase = homeBase;
        }

        public Color GizmoColor()
        {
            return Color.red;
        }

        public void OnEnter()
        {
            kiwiReferences.Animator.SetBool("walk", true);
            kiwiReferences.NavAgent.SetDestination(homeBase.transform.position);
        }

        public void OnExit()
        {
            kiwiReferences.Animator.SetBool("walk", false);
        }

        public void Tick() { }

        public bool HasArrivedAtDestination()
        {
            return kiwiReferences.NavAgent.remainingDistance < 0.1f;
        }
    }
}
