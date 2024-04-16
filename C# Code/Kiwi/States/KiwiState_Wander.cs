using UnityEngine;
using UnityEngine.AI;

namespace CSE5912.PenguinProductions
{
    public class KiwiState_Wander : IState
    {
        private readonly KiwiReferences kiwiReferences;
        private readonly Rooms rooms;

        public KiwiState_Wander(KiwiReferences kiwiReferences, Rooms rooms)
        {
            this.kiwiReferences = kiwiReferences;
            this.rooms = rooms;
        }

        public Color GizmoColor()
        {
            return Color.red;
        }

        public void OnEnter()
        {
            kiwiReferences.Animator.SetBool("walk", true);
            //RoomPosition nextRoom = this.rooms.GetRandomRoomPosition(kiwiReferences.transform.position);
            kiwiReferences.NavAgent.SetDestination(RandomNavmeshLocation(10f));
            AudioManager.Singleton.PersistentPlayOneShot(kiwiReferences.kiwiSFX[0], AudioManager.AudioGroup.FX);
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

        public Vector3 RandomNavmeshLocation(float radius)
        {
            Vector3 randomDirection = Random.insideUnitSphere * radius;

            randomDirection += kiwiReferences.transform.position;


            Vector3 finalPosition = Vector3.zero;

            if (NavMesh.SamplePosition(randomDirection, out NavMeshHit hit, radius, 1))
            {
                finalPosition = hit.position;
            }

            return finalPosition;
        }
    }
}
